document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.querySelector('section:first-of-type form');
    const loginForm = document.querySelector('section:last-of-type form');

    registerForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const name = registerForm.querySelector('input[name="name"]').value;
        const email = registerForm.querySelector('input[name="email"]').value;
        const password = registerForm.querySelector('input[name="password"]').value;

        console.log(['Register', name, email, password]); // Log the tuple
        // You can also return this tuple or use it as needed
        // return ['Register', name, email, password];
    });

    loginForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const name = loginForm.querySelector('input[name="name"]').value;
        const password = loginForm.querySelector('input[name="password"]').value;

        console.log(['Log In', name, password]); // Log the tuple
        // You can also return this tuple or use it as needed
        // return ['Log In', name, password];
    });
});