let idEl = document.querySelector('#txtUsername')
let pwEl = document.querySelector('#txtPassword')
let btnEl = document.querySelector('#btnSubmit')
function login() {
    if (idEl.value == "") {
      idEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        idEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
    else if (pwEl.value == "") {
      pwEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        pwEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
//    else if ('<%=returnRes%>' == 'ID') {
//      alert('존재하지 않는 아이디 입니다.')
//    }
//    else if ('<%=returnRes%>' == 'PW') {
//      alert('비밀번호가 일치하지 않습니다.')
//    }
}