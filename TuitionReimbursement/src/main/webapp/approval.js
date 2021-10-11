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
    } else {
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    }

    for (let r in list) {
        //req = JSON.parse(r);
        let request = document.getElementById('request');
        if ((r.employeeId === user.employeeId) && ((r.benCoApproval === "true") || (r.dsApproval === "true") || (r.dhApproval === "true"))) {
            let option = document.createElement("option");
            option.innerHTML += '<option value="' + r.id + '">' + r.eventType + ' on ' + r.eventDate + '</option>'
            request.appendChild(option);
        }
    }
}

async function approve() {
    let url = "http://localhost:8080/TuitionReimbursement/approval"

    let reqUpdate = null;
    if (user.benCo == "true") {
        reqUpdate = {
            id: document.getElementById('request'),
            benCoApproval: document.getElementById('approve')
        }
    }

    if (ds == "true") {
        reqUpdate = {
            id: document.getElementById('request'),
            dsApproval: document.getElementById('approve')
        }
    }

    if (dh == "true") {
        reqUpdate = {
            id: document.getElementById('request'),
            dhApproval: document.getElementById('approve')
        }
    }

    console.log(reqUpdate);

    let res = await fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(reqUpdate)
    });

    let resJson = await res.text()
    .then(res => {
        console.log(res)
    })
    .catch(error => {
        console.log(error);
    })
}

function loadRequest() {
    let cookie = JSON.parse(getCookie("requestList"));
    let list = JSON.parse(cookie);

    let id = document.getElementById('request').value;

    for (let r in list) {
        if (r.id == id) {
            let table = document.createElement('table');
            table.innerHTML += "<tr>" + '<td>' + r.eventType + '</td>' + '<td>' + r.eventDate + '</td>' + '<td>' + r.grade + '</td>' + "</tr>";
            let element = document.getElementById('display');
            element.appendChild(table);
        }
    }
}