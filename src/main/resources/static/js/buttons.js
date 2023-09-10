let count = 1;

function addButtonRemove() {
    const buttonRemove = document.createElement("button");
    buttonRemove.textContent = "-";
    buttonRemove.setAttribute("onClick", "removeField(" + count + ")");
    buttonRemove.setAttribute("id", "remove"+count);
    buttonRemove.setAttribute("class", "btn btn-outline-warning btn-sm mb-2");
    count++;
    return buttonRemove;
}

function removeField(removed){
    const searchElement = document.getElementById("remove" + removed);
    const removedField = searchElement.previousElementSibling;
    if(removedField){
        searchElement.remove();
        removedField.remove();
    } else {
        console.log("Видаляємий елемент відсутній");
    }
}

function addHIghRiskField(){
    const highRiskField = document.getElementById("highRiskField");
    const originalSelect = document.querySelector('[name="highRiskId"]');
    highRiskField.append(originalSelect.cloneNode(true));
    const buttonRemove = addButtonRemove();
    highRiskField.append(buttonRemove);
}

function addEmployeeField() {
    const employeeField = document.getElementById("employeeFields");
    const originalDatalist = document.getElementById("employeeWork");
    employeeField.append(originalDatalist.cloneNode(true));
    const buttonRemove = addButtonRemove();
    employeeField.appendChild(buttonRemove);
}

function addMachineryField(){
    const machineryField = document.getElementById("machineryField");
    const originalSelect = document.querySelector('[name="machineryId"]');
    machineryField.append(originalSelect.cloneNode(true));
    const buttonRemove = addButtonRemove();
    machineryField.append(buttonRemove);
}

function addEquipmentField(){
    const equipmentField = document.getElementById("equipmentField");
    const originalSelect = document.querySelector('[name="equipmentId"]');
    equipmentField.append(originalSelect.cloneNode(true));
    const buttonRemove = addButtonRemove();
    equipmentField.append(buttonRemove);
}

function addToolField(){
    const toolField = document.getElementById("toolField");
    const originalSelect = document.querySelector('[name="toolId"]');
    toolField.append(originalSelect.cloneNode(true));
    const buttonRemove = addButtonRemove();
    toolField.append(buttonRemove);
}