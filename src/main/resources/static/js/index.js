/* global fetch */

window.onload = () => {
    document.getElementById("bntform").onclick = (e) => {
        e.preventDefault();
        let usuario = {
            nombre: document.getElementById("nombre").value,
            apellidos: document.getElementById("apellidos").value,
            password: document.getElementById("password").value
        }

        fetch("/api/newuser", {
            method: 'POST', // or 'PUT'
            body: JSON.stringify(usuario), // data can be `string` or {object}!
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => res.json())
                .catch(error => console.error('Error:', error))
                .then(response => {
                    console.log('Success:', response);
                    location.reload();
                });

    };
    document.getElementById("btnSearch").onclick = () => {
        let texto = document.getElementById("textsearch").value;
        let url = `/api/search?text=${texto}`;
        fetch(url).then(res => {
             return res.json();
        })
                .catch(error => console.error('Error:', error))
                .then(response => {
                    console.log('Success:', response);

                });

    };


};


