package com.aerotravel.flightticketbooking.controller;

import com.aerotravel.flightticketbooking.model.*;
import com.aerotravel.flightticketbooking.repository.FlightRepository;
import com.aerotravel.flightticketbooking.repository.RoleRepository;
import com.aerotravel.flightticketbooking.repository.UserRepository;
import com.aerotravel.flightticketbooking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    AirportService airportService;
    @Autowired
    AircraftService aircraftService;
    @Autowired
    FlightService flightService;
    @Autowired
    PassengerService passengerService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    VerifyPassengerService verifyPassengerService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlightRepository flightRepository;

    @GetMapping("/")
    public String showHomePage(Model model) {

        // high chart1 불러오기 (도착 여행지 통계)
        List<Passenger> passengerList = passengerService.getAllPassengers();
        Iterator<Passenger> iter = passengerList.iterator();
        HashMap<String, Integer> flightMap = new HashMap<>();
        ArrayList<String> airport = new ArrayList<>();

        while (iter.hasNext()) {
            String airportName = iter.next().getFlight().getDestinationAirport().getAirportName();
            airport.add(airportName); // size 확인
        }
        for (String name : airport) {
            flightMap.put(name, Collections.frequency(airport, name)); // map.size 확인
        }
        model.addAttribute("airportList", flightMap);


        // high chart2 불러오기 (저가 항공사 추천)
        List<Object[]> highChartData = flightRepository.getHighChartData1();
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();

        for (Object[] row : highChartData) {
            data.put((String) row[1], ((Double) row[2]).intValue());
        }

        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());

        return "index";
    }

    @GetMapping("/airport/new")
    public String showAddAirportPage(Model model) {
        model.addAttribute("airport", new Airport());
        return "newAirport";
    }

    @PostMapping("/airport/new")
    public String saveAirport(@Valid @ModelAttribute("airport") Airport airport, BindingResult bindingResult, Pageable pageable, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("airport", new Airport());
            return "newAirport";
        }
        airportService.saveAirport(airport);
        Page<Airport> page = airportService.getAllAirportsPaged(pageable);
        model.addAttribute("airports", page);
        return "airports";
    }

    @GetMapping("/airport/delete")
    public String deleteAirport(@PathParam("airportId") int airportId, Pageable pageable, Model model) {
        airportService.deleteAirport(airportId);
        Page<Airport> page = airportService.getAllAirportsPaged(pageable);
        model.addAttribute("airports", page);
        return "airports";
    }

    @GetMapping("/airports")
    public String showAirportsList(Pageable pageable, Model model) {
        Page<Airport> page = airportService.getAllAirportsPaged(pageable);
        model.addAttribute("airports", page);
        return "airports";
    }

    @GetMapping("/aircraft/new")
    public String showAddAircraftPage(Model model) {
        model.addAttribute("aircraft", new Aircraft());
        return "newAircraft";
    }

    @PostMapping("/aircraft/new")
    public String saveAircraft(@Valid @ModelAttribute("aircraft") Aircraft aircraft, BindingResult bindingResult, Pageable pageable, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("aircraft", new Aircraft());
            return "newAircraft";
        }
        aircraftService.saveAircraft(aircraft);

        Page<Aircraft> page = aircraftService.getAllAircraftsPaged(pageable);
        model.addAttribute("aircrafts", page);
        return "aircrafts";
    }

    @GetMapping("/aircraft/delete")
    public String deleteAircraft(@PathParam("aircraftId") long aircraftId, Pageable pageable, Model model) {
        aircraftService.deleteAircraftById(aircraftId);
        Page<Aircraft> page = aircraftService.getAllAircraftsPaged(pageable);
        model.addAttribute("aircrafts", page);
        return "aircrafts";
    }

    @GetMapping("/aircrafts")
    public String showAircraftsList(Pageable pageable, Model model) {
        Page<Aircraft> page = aircraftService.getAllAircraftsPaged(pageable);
        model.addAttribute("aircrafts", page);
        return "aircrafts";
    }

    @GetMapping("/flight/new")
    public String showNewFlightPage(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
        model.addAttribute("airports", airportService.getAllAirports());
        return "newFlight";
    }

    @PostMapping("/flight/new")
    public String saveFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult bindingResult,
                             @RequestParam("departureAirport") int departureAirport,
                             @RequestParam("destinationAirport") int destinationAirport,
                             @RequestParam("aircraft") long aircraftId,
                             @RequestParam("arrivalTime") String arrivalTime,
                             @RequestParam("departureTime") String departureTime,
                             Pageable pageable,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("flight", new Flight());
            model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
            model.addAttribute("airports", airportService.getAllAirports());
            return "newFlight";
        }
        if (departureAirport == destinationAirport) {
            model.addAttribute("sameAirportError", "Departure and destination airport can't be same");
            model.addAttribute("flight", new Flight());
            model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
            model.addAttribute("airports", airportService.getAllAirports());
            return "newFlight";
        }

        flight.setAircraft(aircraftService.getAircraftById(aircraftId));
        flight.setDepartureAirport(airportService.getAirportById(departureAirport));
        flight.setDestinationAirport(airportService.getAirportById(destinationAirport));
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flightService.saveFlight(flight);

        Page<Flight> page = flightService.getAllFlightsPaged(pageable);
        model.addAttribute("flights", page);
        return "flights";
    }

    @GetMapping("/flight/delete")
    public String deleteFlight(@PathParam("flightId") long flightId, Model model, Pageable pageable) {
        flightService.deleteFlightById(flightId);
        Page<Flight> page = flightService.getAllFlightsPaged(pageable);
        model.addAttribute("flights", page);
        return "flights";
    }

    @GetMapping("/flights")
    public String showFlightsList(Pageable pageable, Model model) {
        Page<Flight> page = flightService.getAllFlightsPaged(pageable);
        model.addAttribute("flights", page);

        return "flights";
    }

    @GetMapping("/flight/search")
    public String showSearchFlightPage(Model model) {
        model.addAttribute("airports", airportService.getAllAirports());
        model.addAttribute("flights", null);
        return "searchFlight";
    }

    @PostMapping("/flight/search")
    public String searchFlight(@RequestParam("departureAirport") int departureAirport,
                               @RequestParam("destinationAirport") int destinationAirport,
                               @RequestParam("departureTime") String departureTime,
                               Model model) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);
        Airport depAirport = airportService.getAirportById(departureAirport);
        Airport destAirport = airportService.getAirportById(destinationAirport);

        if (departureAirport == destinationAirport) {
            model.addAttribute("AirportError", "Departure and destination airport cant be same!");
            model.addAttribute("airports", airportService.getAllAirports());
            return "searchFlight";
        }
        List<Flight> flights = flightService.getAllFlightsByAirportAndDepartureTime(depAirport, destAirport, deptTime);

        if (flights.isEmpty()) {
            model.addAttribute("notFound", "No Record Found!");
        } else {
            model.addAttribute("flights", flights);
        }
        model.addAttribute("airports", airportService.getAllAirports());
        return "searchFlight";
    }

    @GetMapping("/flightStatus")
    public String showflight(Model model) {
        model.addAttribute("airports", airportService.getAllAirports());
        model.addAttribute("flights", null);
        return "flightStatus";
    }

    @PostMapping("/flightStatus")
    public String searchsample(@RequestParam("departureAirport") int departureAirport,
                               @RequestParam("departureTime") String departureTime,
                               Model model) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);
        Airport depAirport = airportService.getAirportById(departureAirport);
        List<Flight> flights = flightService.getAllFlightsByAirportTime(depAirport, deptTime);
        HashMap<String,Integer> map=new HashMap<>();
        if(flights.isEmpty()){
            model.addAttribute("notFound", "No Record Found!");
        }else{
            for(Flight  list:flights){
                String s=list.getDestinationAirport().getAirportName();
                if(map.containsKey(s)){
                    map.put(s,map.get(s)+1);
                }else{
                    map.put(s,1);
                }
            }
            model.addAttribute("flight", flights);
            model.addAttribute("departure",depAirport);
            model.addAttribute("flights", map);
        }

        model.addAttribute("airports", airportService.getAllAirports());
        return "flightStatus";
    }
    @GetMapping("/flight/book")
    public String showBookFlightPage(Model model) {
        model.addAttribute("airports", airportService.getAllAirports());
        return "bookFlight";
    }

    @PostMapping("/flight/book")
    public String searchFlightToBook(@RequestParam("departureAirport") int departureAirport,
                                     @RequestParam("destinationAirport") int destinationAirport,
                                     @RequestParam("departureTime") String departureTime,
                                     Model model) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);
        Airport depAirport = airportService.getAirportById(departureAirport);
        Airport destAirport = airportService.getAirportById(destinationAirport);

        if (departureAirport == destinationAirport) {
            model.addAttribute("AirportError", "Departure and destination airport cant be same!");
            model.addAttribute("airports", airportService.getAllAirports());
            return "bookFlight";
        }
        List<Flight> flights = flightService.getAllFlightsByAirportAndDepartureTime(depAirport, destAirport, deptTime);
        if (flights.isEmpty()) {
            model.addAttribute("notFound", "No Record Found!");
        } else {
            model.addAttribute("flights", flights);
        }
        model.addAttribute("airports", airportService.getAllAirports());
        return "bookFlight";
    }

    @GetMapping("/flight/book/new")
    public String showCustomerInfoPage(@RequestParam long flightId, Model model) {
        model.addAttribute("flightId", flightId);
        model.addAttribute("passenger", new Passenger());
        return "newPassenger";
    }

    @PostMapping("/flight/book/new")
    public String bookFlight(@Valid @ModelAttribute("passenger") Passenger passenger, BindingResult bindingResult, @RequestParam("flightId") long flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        Passenger passenger1 = passenger;
        passenger1.setFlight(flight);
        passengerService.savePassenger(passenger1);
        model.addAttribute("passenger", passenger1);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        User user = userRepository.findByusername(username);
        VerifyPassenger verifyPassenger = new VerifyPassenger(flight, passenger, user);
        verifyPassengerService.saveVerifyPassenger(verifyPassenger);
        return "confirmationPage";
    }

    @GetMapping("/flight/book/verify")
    public String showVerifyBookingPage(Model model, Pageable pageable) {

        boolean adminChk = false;
        // 로그인을 한 유저 (시큐리티로 부터 얻을 수 있음)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {

            if(authority.toString().equals("ROLE_ADMIN")){
                adminChk = true;
            }
        }

        Page<VerifyPassenger> verifyPassengers = null;
        // admin 계정일 떈 모든 예약정보 다 보이게
        if(adminChk){
            verifyPassengers = verifyPassengerService.getAllVerifyPassenger(pageable);
        }else{
            String username = userDetails.getUsername();
            User users= userRepository.findByusername(username);
            model.addAttribute("users",users);
            verifyPassengers = verifyPassengerService.getAllVerifyPassenger(users,pageable);
        }

        if (verifyPassengers != null) {
            model.addAttribute("verifyPassengers",verifyPassengers);
        } else {
            model.addAttribute("notFound", "Not Found");
            return "verifyBooking";
        }
        return "verifyBooking";
    }


    @PostMapping("/flight/book/cancel")
    public String cancelTicket(@RequestParam("passengerId") long passengerId,@RequestParam("verifypassengerId") long verifypassengerId, Model model){
        verifyPassengerService.deleteVerifyPassengerById(verifypassengerId);
        passengerService.deletePassengerById(passengerId);
        return "redirect:/flight/book/verify";

    }

    @GetMapping("passengers")
    public String showPassengerList(@RequestParam long flightId, Model model) {
        List<Passenger> passengers = flightService.getFlightById(flightId).getPassengers();
        model.addAttribute("passengers", passengers);
        model.addAttribute("flight", flightService.getFlightById(flightId));
        return "passengers";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String showSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(User user) {

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 권한 설정 (다대다 매핑 주의,,,)
        List<Role> roleList = new ArrayList<>();
        Optional<Role> optional = roleRepository.findById(2);
        roleList.add(optional.get());
        user.setRoles(roleList);

        userService.signUp(user);

        return "redirect:/";
    }

    @RequestMapping("/signUp/usernameChk")
    @ResponseBody
    public Map<String, Object> usernameChk(@RequestParam String username) {
        System.out.println("###############username controller");
        Map<String, Object> mv = new HashMap<>();
        mv.put("result", userService.usernameChk(username));
        return mv;
    }

    @GetMapping("fancy")
    public String showLoginPage1() {
        return "index";
    }
}
