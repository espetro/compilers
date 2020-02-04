// funciones utilizadas para anular preguntas
///////////////////////////////////////////////////////////////////////////////
function actualizarPregunta (i) {
	try {
		var req = jsGetXmlHttpRequest();
		if(req.readyState==4 && req.status==200) {
			var salida = req.response;
			if (salida.includes('null')) {
				document.getElementById("anular"+i).checked = !document.getElementById("anular"+i).checked;
			} else if (salida.includes('on')) {
				document.getElementById("anular"+i).checked = true;
			} else if (salida.includes('off')) {
				document.getElementById("anular"+i).checked = false;
			}
		} else if (xmlHttp.status!=200) {
		} else if (req.statusText!= '' && xmlHttp.status!=200) {
	        alert("Error loading page: " + req.statusText);
		}
	} catch (err) {}
}

///////////////////////////////////////////////////////////////////////////////
function anularInstancia(i, idPregunta, md5) {
	var input = document.getElementById("anular"+i);
	var oper = 'off';
	if (input.checked) {
		oper = 'on';
	}
	var url = URLAnularPregunta + '?idPregunta='+idPregunta+'&md5='+md5+'&oper='+oper;
	var listener = function () {
		actualizarPregunta(i);	
	}	
	jsStartAjaxRequest(url, listener);
}
