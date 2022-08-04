const idEl = document.querySelector('#txtUsername')
const pwEl = document.querySelector('#txtPassword')
const pwCheckEl = document.querySelector('#txtPassword-check')
const firstNameEl = document.querySelector('#txtFirstname')
const lastNameEl = document.querySelector('#txtLastname')
const emailEl = document.querySelector('#txtEmail')
const btnEl = document.querySelector('#btnSubmit')

const pwSpanEl = document.querySelector('span.pwspan')
const pwCheckSpanEl = document.querySelector('span.pwcheckspan')
const form = document.querySelector('form')
const sameChkEl = document.querySelector('sameck')
const idCheckEl = document.querySelector('span.id-check')

function pwValCheck() {
    let pw_passed = true
    let pattern1 = /[0-9]/;
    let pattern2 = /[a-zA-Z]/;
    let pattern3 = /[~!@\#$%<>^&*]/;     // 원하는 특수문자 추가 제거
    let pw_msg = "";
    if (pwEl.value == "") {
      pwCheckEl.value = ''
      pwSpanEl.textContent = ''
      pwCheckSpanEl.textContent = ''
      return false
    }
    else if (pwEl.value.indexOf(idEl.value) > -1) {
      pwSpanEl.classList.add('cannotid')
      pwSpanEl.textContent = '비밀번호는 ID를 포함할 수 없습니다.'
      return false
    }
    else if(!pattern1.test(pwEl.value) || !pattern2.test(pwEl.value) || !pattern3.test(pwEl.value) || pwEl.value.length < 8 || pwEl.value.length > 50) {
      pwSpanEl.classList.add('plscheck')
      pwSpanEl.textContent = '영문+숫자+특수기호 8자리 이상으로!'
      return false
    }
    else if (pattern1.test(pwEl.value) && pattern2.test(pwEl.value) && pattern3.test(pwEl.value) && pwEl.value.length >= 8 && pwEl.value.length <= 50) {
      pwSpanEl.classList.remove('plscheck')
      pwSpanEl.classList.remove('cannotid')
      pwSpanEl.textContent = '훌륭한 비밀번호네요!'
      return true
    }
}

function pwSameCheck() {
    if (pwEl.value == "" || pwCheckEl.value == "") {
      pwCheckSpanEl.textContent = ''
      return false
    }
    else if (pwEl.value != "" && pwCheckEl.value != "") {
      if (pwEl.value != pwCheckEl.value) {
        pwCheckSpanEl.classList.add('different')
        pwCheckSpanEl.textContent = '비밀번호가 일치하지 않습니다.'
        return false
      }
      else if (pwEl.value == pwCheckEl.value) {
        pwCheckSpanEl.classList.remove('different')
        pwCheckSpanEl.textContent = '비밀번호가 일치합니다.'
        return true
      }
    }
}

// 한글 입력 방지 -> 한글은 삭제
idEl.onkeyup = function() {
    var v = this.value;
    this.value = v.replace(/[^a-z0-9]/gi, '');
    $('.sameck').attr('onclick', 'usernameCheck();');
    $('.sameck').css('pointer-events', 'auto');
}

// 숫자만 입력
//phoneEl.onkeyup = function() {
//    var regexp = /[^0-9]/gi;
//    this.onkeyup = function(e){
//      var v = this.value;
//      this.value = v.replace(regexp,'');
//    }
//}

// submit(회원가입 버튼) 시 유효성 체크
function register() {
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
    else if (pwCheckEl.value == "") {
      pwCheckEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        pwCheckEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
    else if (firstNameEl.value == "") {
      firstNameEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        firstNameEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
    else if (lastNameEl.value == "") {
      lastNameEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        lastNameEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
    else if (emailEl.value == "") {
      emailEl.nextElementSibling.classList.add('warning')
      setTimeout(function() {
        emailEl.nextElementSibling.classList.remove('warning')
      }, 1500)
    }
    else if ($('.sameck').attr('onclick') != null) {
      return false
    }
    else {return true}
}

function formCheck() {
    if (pwValCheck() && pwSameCheck() && register()){
      alert('회원가입 성공~!~!')
      return true
    }
    else if (!register()) {
      alert('중복 체크해주세요!!')
      return false
    }
    else {
      alert('다시 한번 확인해주세요~')
      return false
    }
}

function usernameCheck() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "post",
        url: "/signUp/usernameChk",
        data: {"username": idEl.value},
        beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
        },
        success: function (result) {

            if (idEl.value == "") {
              idEl.nextElementSibling.classList.add('warning')
              setTimeout(function() {
                idEl.nextElementSibling.classList.remove('warning')
              }, 1500)
            } else {
                if (result.result == "0") {
                    alert("사용 가능한 아이디 입니다.");
                    $('.sameck').removeAttr('onclick');
                    $('.sameck').css('pointer-events', 'none');
                } else if (result.result == "1") {
                    alert("이미 사용중인 아이디 입니다.");
                    $("#username").focus();
                } else {
                    alert("success이지만 result 값이 undefined 잘못됨");
                }
            }
        },
        error: function (request, status,error) {
            alert("ajax 실행 실패");
            alert("code:" + request.status + "\n" + "error :" + error);
        }
    });
}

//$('.sameck').click(function() {
//    $.ajax({
//    method : 'post',
//    url : '/idcheck',
//    data : {id : id.value}
//    }).done(function(결과){
//      console.log($('#id').val())
//      if ($('#id').val() == '') {
//        $('#id').next().addClass('warning')
//        setTimeout(() => {
//          $('#id').next().removeClass('warning')
//        }, 1500);
//        $('.id-check').text('')
//      }
//      else {
//        if (결과.checkRes == 1) {
//        $('.id-check').text('사용 가능한 아이디 입니다.')
//        $('.id-check').css('color', 'green')
//      }
//      else {
//        $('.id-check').text('중복된 아이디 입니다.')
//        $('.id-check').css('color', 'red')
//      }
//      }
//    }).fail(function() {
//    })
//})