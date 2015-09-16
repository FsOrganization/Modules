(function() {
    var singleFile = (typeof ModulesImport == "object" && ModulesImport.singleFile);
    window.ModulesImport = {
        _scriptName: (!singleFile) ? "js/ModulesEasyUI.js'," : "ModulesEasyUI.js',",
        _getScriptLocation: function () {
            var scriptLocation = "";
            var scriptName = ModulesImport._scriptName;
            var scripts = document.getElementsByTagName('script');
            for (var i=0, len=scripts.length; i<len; i++) {
                var src = scripts[i].getAttribute('src');
                if (src) {
                    var index = src.lastIndexOf(scriptName); 
                    // set path length for src up to a query string
                    var pathLength = src.lastIndexOf('?');
                    if (pathLength < 0) {
                        pathLength = src.length;
                    }
                    // is it found, at the end of the URL?
                    if ((index > -1) && (index + scriptName.length == pathLength)) {
                        scriptLocation = src.slice(0, pathLength - scriptName.length);
                        break;
                    }
                }
            }
            return scriptLocation;
         }
    };
    if(!singleFile) {
        var jsfiles = new Array(
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.accordion.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.calendar.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.combo.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.combobox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.combogrid.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.combotree.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.datagrid.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.datebox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.datetimebox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.datetimespinner.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.dialog.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.draggable.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.droppable.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.filebox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.form.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.layout.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.linkbutton.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.menu.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.menubutton.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.messager.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.numberbox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.numberspinner.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.pagination.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.panel.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.parser.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.progressbar.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.propertygrid.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.resizable.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.searchbox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.slider.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.spinner.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.splitbutton.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.tabs.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.textbox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.timespinner.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.tooltip.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.tree.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.treegrid.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.validatebox.js',
//        		'/Modules/EasyUI_1.4.1/plugins/jquery.window.js',
//        		'/Modules/EasyUI_1.4.1/locale/easyui-lang-en.js',
        		'/Modules/EasyUI_1.4.1/locale/easyui-lang-zh_CN.js',
//        		'/Modules/EasyUI_1.4.1/locale/easyui-lang-zh_TW.js',
        		'/Modules/EasyUI_1.4.1/easyloader.js',
        		'/Modules/EasyUI_1.4.1/jquery.easyui.min.js'
//        		'/Modules/EasyUI_1.4.1/jquery.min.js'
        );
        var cssfiles = new Array(
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/accordion.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/calendar.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/combo.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/combobox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/datagrid.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/datebox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/dialog.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/easyui.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/filebox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/layout.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/linkbutton.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/menu.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/menubutton.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/messager.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/numberbox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/pagination.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/panel.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/progressbar.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/propertygrid.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/searchbox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/slider.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/spinner.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/splitbutton.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/tabs.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/textbox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/tooltip.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/tree.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/validatebox.css',
        		'/Modules/EasyUI_1.4.1/themes/bootstrap/window.css',
        		'/Modules/EasyUI_1.4.1/themes/color.css',
        		'/Modules/EasyUI_1.4.1/themes/icon.css'
            );
        var agent = navigator.userAgent;
        var docWrite = (agent.match("MSIE") || agent.match("Safari") || agent.match("Firefox") || agent.match("Gecko") || agent.match("Camino"));
        var allScriptTags = undefined;
        if(docWrite) {
            allScriptTags = new Array(jsfiles.length);
        }
        var host = ModulesImport._getScriptLocation();
        for (var i=0, len=jsfiles.length; i<len; i++) {
            if (docWrite) {
                allScriptTags[i] = "<script src='" + host + jsfiles[i] +"'></script>";
            } else {
                var s = document.createElement("script");
                s.src = host + jsfiles[i];
                var h = document.getElementsByTagName("head").length ? document.getElementsByTagName("head")[0] : document.body;
                h.appendChild(s);
            }
        }
        if (docWrite) {
            document.write(allScriptTags.join(""));
        }

        var allCssTags = undefined;
        var docCssWrite = (agent.match("MSIE") || agent.match("Safari") || agent.match("Firefox") || agent.match("Gecko") || agent.match("Camino"));
        if(docCssWrite) {
            allCssTags = new Array(cssfiles.length);
        }
        for (var i=0, len=allCssTags.length; i<len; i++) {
            if (docCssWrite) {
            	allCssTags[i] = "<link rel='StyleSheet' href='" + host + cssfiles[i] +"'type='text/css' media='screen'>"; 
            } else {
            	var h = document.getElementsByTagName("head").length ? document.getElementsByTagName("head")[0] : document.body;
                var link = document.createElement('link');
                link .setAttribute('rel','stylesheet');  
                link .setAttribute('href',host + cssfiles[i]);  
                link .setAttribute('type','text/css');  
                h.appendChild(link);
               
            }
        }
        if (docCssWrite) {
            document.write(allCssTags.join(""));
        }
    }
})();