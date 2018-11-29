function setCaretPosition(element, index) {
    if (element.setSelectionRange) {
        element.focus();
        element.setSelectionRange(index, index);
    }
    else if (element.createTextRange) {
        var range = element.createTextRange;
        range.collapse(true);
        range.moveEnd('character', index);
        range.moveStart('character', index);
        range.select();
    }
}



