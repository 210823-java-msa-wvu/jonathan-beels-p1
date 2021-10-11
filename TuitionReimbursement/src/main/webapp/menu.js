async function getRequests() {
    let url = "http://localhost:8080/TuitionReimbursement/menu"

    let res = await fetch(url, {
        method: "GET",
        headers: {"Content-Type": "application/json"}
    });

    createList();
}

object.addEventListener("load", getRequests);



function createList() {
    let userCookie = JSON.parse(getCookie("employee"));
    let user = JSON.parse(userCookie);
    let cookie = JSON.parse(getCookie("requestList"));
    let list = JSON.parse(cookie);
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);
    
    for (let r in list) {
        if (r.employeeId === user.employeeId) {
            let row = document.createElement("tr");
           
            row.innerHTML += '<td>' + r.eventType + '</td>' + '<td>' + r.eventDate + '</td>' + '<td>' + r.grade + '</td>';

            let element = document.getElementById("table");
            element.appendChild(row);
        }

    }

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