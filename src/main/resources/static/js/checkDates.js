function checkDate(checkDate, message, condition) {
    return function () {
        const currentDate = new Date();
        const inputDate = new Date(checkDate.value);
        currentDate.setHours(0, 0, 0, 0);
        inputDate.setHours(0, 0, 0, 0);
        if (condition(currentDate, inputDate)) {
            alert(message);
            checkDate.value = "";
        }
    };
}

function isInputDateBeforeCurrentDate(currentDate, inputDate) {
    return inputDate.getTime() < currentDate.getTime();
}

function isInputDateAfterCurrentDate(currentDate, inputDate) {
    return inputDate.getTime() > currentDate.getTime();
}

function isInputDateNotEqualToCurrentDate(currentDate, inputDate) {
    return inputDate.getTime() !== currentDate.getTime();
}