Object.addEventListener("load", load);

function load() {
    let userCookie = JSON.parse(getCookie("employee"));
    let user = JSON.parse(userCookie);
    let cookie = JSON.parse(getCookie("requestList"));
    let list = JSON.parse(cookie);
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    if ((user.benCo === "true") || (ds === "true") || (dh === "true")) {
        let li = document.createElement("li");
        li.setAttribute("class", "nav-item");

        let a = document.createElement("a");
        a.setAttribute("class", "nav-link");
        a.setAttribute("href", "http://localhost:8080/TuitionReimbursment/approval.html");

        li.appendChild(a);

        let element = document.getElementById("navbar");
        element.appendChild(li);
    }
}

async function submit() {
    let userCookie = JSON.parse(getCookie("employee"));
    let user = JSON.parse(userCookie);
    
    let url = "http://localhost:8080/TuitionReimbursment/requests";

    let req = {
        employeeId: user.id,
        amount: document.getElementById('amount').value,
        eventType: document.getElementById('eventType').value,
        eventDate: document.getElementById('eventDate').value,
        eventTime: document.getElementById('eventTime').value,
        location: document.getElementById('eventLocation').value,
        description: document.getElementById('description').value,
        gradingFormat: document.getElementById('gradingFormat').value,
        justification: document.getElementById('justification').value,
        urgent: document.getElementById('urgent').value
    }

    console.log(req);

    let res = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });

    let resJson = await res.text()
    .then (res => {
        console.log(res);
    })
    .catch( error => {
        console.log(error);
    })
}
