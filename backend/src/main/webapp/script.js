// LOGIN
// LOGIN
let loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", function(event) {

        let email = document.getElementById("email").value;
        let password = document.getElementById("password").value;

        if (email === "" || password === "") {
            event.preventDefault();
            alert("Please enter email and password");
        }

        // If details are entered,
        // form will be submitted to LoginServlet.
    });
}


// REGISTER
let registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", function(event) {

        let password = document.getElementById("password").value;
        let confirmPassword =
            document.getElementById("confirmPassword").value;

        if (password !== confirmPassword) {

            event.preventDefault();

            alert("Passwords do not match");
        }

    });
}