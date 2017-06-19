/**
 * 
 */
function setSelectfield(fieldId) {
	var s = "<select name='" + fieldId + "'><option value='CompanyName'>Company Name</option>\n";
	s += "<option value='TicketId'>Ticket ID</option>\n<option value='ContactName'>Contact Name</option></select>\n"
	var element = document.getElementById(fieldId);
	alert(s);
	element.innerHTML = s;
}