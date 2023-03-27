var card = document.getElementById("card");
var container = document.querySelector(".container")
        function openRegister(){
            card.style.transform = "rotateY(-180deg)";
            card.style.height = "550px";
            
        }
        function openLogin() {
            card.style.transform = "rotateY(0deg)";
        }

var pass = document.querySelector('#rpassword');
var msg = document.querySelector('#message');
var str = document.querySelector("#strength");

var cpass = document.querySelector("#rcpassword")

        pass.addEventListener('keyup', () => {
            if(pass.value.length > 0){
                msg.style.display = "block";
            }
            else{
                msg.style.display = "none";
            }
            if(pass.value.length < 4){
                str.innerHTML = "weak";
                pass.style.borderColor = "#ff5925";
                msg.style.color = "#ff5925";
            } 
            
            else if(pass.value.length >= 4 && pass.value.length < 8){
                str.innerHTML = "medium";
                pass.style.borderColor = "#ff5925";
                msg.style.color = "#ff5925";
            }
            else if (pass.value.length >= 8){
                str.innerHTML = "strong";
                pass.style.borderColor = "#26d730";
                msg.style.color = "#26d730";
            }
        })

var msg2 = $('#message2');
var msg3 = $('#message3');
var submitbtn = $('.submit-btn');
$('#rpassword').keyup(function(){
    msg2.css({
        "display": "none"
    })
})
$('#pin').keyup(function () {
    msg3.css({
        "display": "none"
    })
})
submitbtn.click(function(e){
    e.preventDefault();
    if ($('#rpassword').val() != $('#rcpassword').val() && $('#rpassword').val().length>0){
        msg2.css({
            "display": "block"
        })
    }
    if ($('#pin').val() != $('#cpin').val() && $('#pin').val().length>0){
        msg3.css({
            "display": "block"
        })
    }
})