///////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------
// Esta librer�a se encarga de modelar todo lo referente a la correcci�n de los
// �tems abiertos.
// �mbito de uso: la JSP 'testCorregido.jsp'.
// @author guzman
//-----------------------------------------------------------------------------
// include "/js/ajax.js"
//-----------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////
function jsParseParam(obj, orden, url) {
  var getstr = "?";
  for (i=0; i<obj.getElementsByTagName("input").length; i++) {
        if (obj.getElementsByTagName("input")[i].type == "text") {
           getstr += obj.getElementsByTagName("input")[i].name + "=" + 
                   obj.getElementsByTagName("input")[i].value + "&";
        }
        if (obj.getElementsByTagName("input")[i].type == "checkbox") {
           if (obj.getElementsByTagName("input")[i].checked) {
              getstr += obj.getElementsByTagName("input")[i].name + "=" + 
                   obj.getElementsByTagName("input")[i].value + "&";
           //} else {
           ///   getstr += obj.getElementsByTagName("input")[i].name + "=&";
           }
        }
        if (obj.getElementsByTagName("input")[i].type == "radio") {
           if (obj.getElementsByTagName("input")[i].checked) {
              getstr += obj.getElementsByTagName("input")[i].name + "=" + 
                   obj.getElementsByTagName("input")[i].value + "&";
           }
     }  
     if (obj.getElementsByTagName("input")[i].tagName == "SELECT") {
        var sel = obj.getElementsByTagName("input")[i];
        getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
     }
     if (obj.getElementsByTagName("input")[i].type == "hidden") {
        getstr += obj.getElementsByTagName("input")[i].name + "=" + 
             obj.getElementsByTagName("input")[i].value + "&";
     }     
  }
  return getstr;
}   
///////////////////////////////////////////////////////////////////////////////
function jsMakeCorrection(obj, orden, url) {
	var getstr = jsParseParam(obj, orden, url);
	var listener = function () {
		actualizarTextoPregunta(orden);
	}	
	jsStartAjaxRequest(url + getstr, listener);
}   
///////////////////////////////////////////////////////////////////////////////
function jsUndoCorrection(obj, orden, url) {
	var getstr = jsParseParam(obj, orden, url);
    getstr += "undo=1";
	var listener = function () {
		actualizarTextoPregunta(orden);
	}	
	jsStartAjaxRequest(url + getstr, listener);
}   
///////////////////////////////////////////////////////////////////////////////
function actualizarTextoPregunta(indice) {
	try {
		var req = jsGetXmlHttpRequest();
		if(req.readyState==4 && req.status==200) {
			var salida = req.responseXML.getElementsByTagName("salida")[0];
			var texto;
			if(window.ActiveXObject) {
				texto = salida.firstChild.data;
			} else {
				texto = salida.textContent;
			}
			jsSetPregunta (indice, texto);
		} else if (xmlHttp.status!=200) {
		} else if (req.statusText!= '' && xmlHttp.status!=200) {
	        alert("Error loading page: " + req.statusText);
		}
	} catch (err) {}
}

///////////////////////////////////////////////////////////////////////////////
function jsBorrarFicherosExtra(obj, numPreg, url) {
	var getstr = "?numPreg="+numPreg;
	var listener = function () {
		borrarFicherosExtra (numPreg);	
	}	
	jsStartAjaxRequest(url + getstr, listener);
}   
///////////////////////////////////////////////////////////////////////////////
function borrarFicherosExtra(numPreg) {
	try {
		var req = jsGetXmlHttpRequest();
		if(req.readyState==4 && req.status==200) {
			var texto = req.responseText;
			var iframe = document.getElementById("ficherosFrame"+numPreg);
			var doc; 
			if(iframe.contentDocument) { 
			    doc = iframe.contentDocument; 
			} else {
			    doc = iframe.contentWindow.document; 
			}
			doc.body.innerHTML=texto;
		} else if (xmlHttp.status!=200) {
		} else if (req.statusText!= '' && xmlHttp.status!=200) {
	        alert("Error loading page: " + req.statusText);
		}
	} catch (err) {}
}

