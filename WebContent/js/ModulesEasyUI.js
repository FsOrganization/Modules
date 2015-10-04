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
//        		'/Express/EasyUI_1.4.1/plugins/jquery.accordion.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.calendar.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.combo.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.combobox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.combogrid.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.combotree.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.datagrid.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.datebox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.datetimebox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.datetimespinner.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.dialog.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.draggable.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.droppable.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.filebox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.form.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.layout.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.linkbutton.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.menu.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.menubutton.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.messager.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.numberbox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.numberspinner.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.pagination.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.panel.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.parser.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.progressbar.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.propertygrid.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.resizable.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.searchbox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.slider.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.spinner.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.splitbutton.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.tabs.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.textbox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.timespinner.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.tooltip.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.tree.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.treegrid.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.validatebox.js',
//        		'/Express/EasyUI_1.4.1/plugins/jquery.window.js',
//        		'/Express/EasyUI_1.4.1/locale/easyui-lang-en.js',
        		'/Express/EasyUI_1.4.1/locale/easyui-lang-zh_CN.js',
//        		'/Express/EasyUI_1.4.1/locale/easyui-lang-zh_TW.js',
        		'/Express/EasyUI_1.4.1/easyloader.js',
        		'/Express/EasyUI_1.4.1/jquery.easyui.min.js'
//        		'/Express/EasyUI_1.4.1/jquery.min.js'
        );
        var cssfiles = new Array(
        		'/Express/EasyUI_1.4.1/themes/bootstrap/accordion.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/calendar.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/combo.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/combobox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/datagrid.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/datebox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/dialog.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/easyui.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/filebox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/layout.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/linkbutton.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/menu.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/menubutton.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/messager.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/numberbox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/pagination.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/panel.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/progressbar.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/propertygrid.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/searchbox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/slider.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/spinner.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/splitbutton.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/tabs.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/textbox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/tooltip.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/tree.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/validatebox.css',
        		'/Express/EasyUI_1.4.1/themes/bootstrap/window.css',
        		'/Express/EasyUI_1.4.1/themes/color.css',
        		'/Express/EasyUI_1.4.1/themes/icon.css'
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