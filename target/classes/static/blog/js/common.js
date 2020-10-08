/**
 * judge null obj
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}
/**
 * verify email
 *
 * @param email
 * @returns {boolean}
 */
function validEmail(email) {
    var pattern =  /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (pattern.test(email.trim())) {
        return (true);
    } else {
        return (false);
    }
}
/**
 * verify phone number
 *
 * @param number
 * @returns {boolean}
 */
function validPhoneNumber(PhoneNumber) {
    var pattern =  /^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/;
    if (pattern.test(PhoneNumber)) {
        return (true);
    } else {
        return (false);
    }
}
