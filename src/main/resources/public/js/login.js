const name = document.getElementById("username-field");
const password = document.getElementById("password-field");

const button = document.querySelector(".login-button");
const logout = document.querySelector(".log-out");

button.addEventListener("click", function(e) {
    e.preventDefault();
    const nameData = name.value;
    const pwData = password.value;
    const serverRequest = {"username": nameData, "password": pwData}
    sendAndSaveLoginRequest(serverRequest);

});

logout.addEventListener("click", function () {
    localStorage.clear();
    alert("Logged out");
})


function sendAndSaveLoginRequest(input) {
    fetch('/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(input)
    })
        .then(response => response.json())
        .then(response => {
            try {
                if (response.token == null) throw new Error();
                const token = response.token;
                localStorage.setItem("token", token)
                alert("You logged in")
            } catch (e) {
                console.log(e)
                alert("Incorrect username or password")
            }

        });

}