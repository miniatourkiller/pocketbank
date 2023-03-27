var card = document.getElementById("card");
var container = document.querySelector(".container")
        function openRegister(){
            card.style.transform = "rotateY(-180deg)";
            card.style.height = "350px";
            
        }
        function openLogin() {
            card.style.transform = "rotateY(0deg)";
        }


$('.nav').load("../navbar/nav.html")
$('.delete').click(function(){
$('.menushowed').toggle()
})
