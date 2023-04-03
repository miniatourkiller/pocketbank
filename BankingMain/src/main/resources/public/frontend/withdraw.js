document.querySelector('.submit-btn').addEventListener('click', function (e) {
    e.preventDefault()
    var mydata = {
        "amount": document.querySelector('.amount').value,
        "agentNo": document.querySelector('.agt').value,
        "pin": document.querySelector('#pin').value
    }
    $.ajax({
        type: "GET",
        url: "http://localhost:8089/agentsuggestion/" + document.querySelector('.agt').value,
        dataType: "text",
        success: function (data) {
            if (data == "expired") {
                window.location = "./account/index.html"
            } else if (data == "no atm") {
                alert("there is no such agent")
            } else {
                if (confirm("Withdraw at " + data)) {
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8089/withdraw",
                        contentType: "application/json",
                        data: JSON.stringify(mydata),
                        success: function (data) {
                            if (data == "expired") {
                                window.location = "./account/index.html"
                            } else if (data == "wrong pin") {
                                alert(data)
                            } else if (data == "insufficient") {
                                alert("insufficient funds")
                            } else if (data == "done") {
                                alert(data);
                                window.location.reload();
                            }
                        }
                    })
                } else {
                    return;
                }
            }
        }
    })
})