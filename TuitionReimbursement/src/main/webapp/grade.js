window.addEventListener("load", getRequests());
let list;

function load() {
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    if ((benCo == true) || (ds == true) || (dh == true)) {
        console.log("flag Approval");
        let li = document.createElement("li");
        let element = document.getElementById("navbar");
        li.innerHTML += '<li class="nav-item"><a class="nav-link" href="http://localhost:8080/TuitionReimbursement/approval.html">Approve Request</a></li>'
        element.appendChild(li);
    }

    for (let i = 0; i < list.length; i++) {
        let r = list[i];
        console.log(r);
        //req = JSON.parse(r);
        let request = document.getElementById('request');
        if ((r.employeeId === userId)) {
            let option = document.createElement("option");
            option.setAttribute("value", r.id);
            option.innerHTML += '<option value="' + r.id + '">' + r.eventType + ' on ' + r.eventDate + '</option>'
            request.appendChild(option);
        }
    }
}

async function submitGrade() {
    let url = "http://localhost:8080/TuitionReimbursement/grade"

    let e = document.getElementById('request');
    let reqUpdate = {
        id: e.options[e.selectedIndex].value,
        grade: document.getElementById('grade').value
    }

    console.log(reqUpdate);

    let res = await fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(reqUpdate)
    });

    let resJson = await res.text()
    .then(res => {
        console.log(res);
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    })
    .catch(error => {
        console.log(error);
    })
}

async function getRequests() {
    let url = "http://localhost:8080/TuitionReimbursement/grade"

    let res = await fetch(url, {
        method: "GET",
        headers: {"Content-Type": "application/json"}
    }).then(res => res.json())
    .then(data => {
        console.log(data);
        list = data;
        console.log(list);
    });

    load();
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }