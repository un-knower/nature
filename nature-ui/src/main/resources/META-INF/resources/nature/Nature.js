/**
 * Nature JavaScript Library v1.1.2
 * 
 * Copyright 2015, 2022 jQuery Foundation, Inc. and other contributors Released under the MIT license
 * 
 * Date: 2017-2-4
 */
window.Nature = function Nature()
{
	if (this != Nature) throw new Error("The Nature class can't be instantiated.");
	if (this.initialization === true) throw new Error("The Nature Has been initialized.");

	// 定义window异常捕获
	window.onerror = function(message, url, lineNumber, columnNumber, error)
	{
		var errorName, errorCode, stackInfo;
		if (error)
		{
			message = error.message ? error.message : (error.description ? error.description : message);
			url = error.fileName ? error.fileName : (error.script ? error.script : url);
			lineNumber = error.lineNumber ? error.lineNumber : lineNumber;
			columnNumber = error.columnNumber ? error.columnNumber : columnNumber;
			errorName = error.name;
			errorCode = error.number;
			stackInfo = error.stack;
		}

		var error_info = "Message: " + message + "\n";
		error_info += ("Script URI: " + url + "\n");
		error_info += ("Error Location: [ lineNumber=" + lineNumber);
		if (columnNumber != undefined) error_info += (", columnNumber=" + columnNumber);
		error_info += " ]\n";
		if (errorCode != undefined) error_info += ("Error Code: " + errorCode + "\n");
		if (errorName != undefined) error_info += ("Error Name: " + errorName + "\n");
		if (stackInfo != undefined) error_info += ("Stack Info: " + stackInfo + "\n");
		this.Nature.log(error_info);
		this.alert(error_info);
		return false;
	};

	this.extend = function(curSource, destSource)
	{
		if ((typeof curSource == "object" || typeof curSource == "function") && typeof destSource == "object")
		{
			for ( var _attr in destSource)
				curSource[_attr] = destSource[_attr];
		}
	};

	this.extend(window.Function.prototype, {
		bind: function(_obj, _args)
		{
			var _m = this, __args = [];
			if (_args != undefined)
			{
				if (Array.isArray(_args) || ("callee" in _args)) __args.concat(Array.prototype.slice.call(_args));
				else __args.push(_args);
			}
			return function()
			{
				_m.apply(_obj, __args.concat(Array.prototype.slice.call(arguments)));
			};
		},
		newInstance: function()
		{
			if (arguments.length > 0)
			{
				var F = function()
				{
				};
				F.prototype = this.prototype;
				var o = new F();
				this.apply(o, arguments);
				return o;
			}
			else return new this();
		},
		extend: function(source)
		{
			if (typeof source == "function")
			{
				// 创建一个中间函数对象以获取父类的原型对象
				var F = function()
				{
				};

				// 设置父类原型对象
				F.prototype = source.prototype;

				// 实例化F, 继承父类的原型中的属性和方法，而无需调用父类的构造函数实例化无关的父类成员
				this.prototype = new F();

				// 设置构造函数指向自己
				this.prototype.constructor = this;

				// 添加一个指向父类构造函数的引用，方便调用父类方法或者调用父类构造函数
				this._super_class = source;
			}
			else if (typeof source == "object") Nature.extend(this.prototype, source)
		}
	});

	window.String.extend({
		trim: function()
		{
			return this.replace(/^\s+|\s+$/g, "");
		},
		toJSON: function()
		{
			if (window.JSON && window.JSON.parse) return window.JSON.parse(this);
			return new Function("return " + this + ";").call(window);
		},
		contains: function(pattern)
		{
			return this.indexOf(pattern) > -1;
		},
		startsWith: function(pattern)
		{
			return this.indexOf(pattern) === 0;
		},
		endsWith: function(pattern)
		{
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceAll: function(s1, s2)
		{
			return this.replace(new RegExp(s1, "gm"), s2);
		},
		camelCase: function()
		{
			return this.replace(/-([a-z])/ig, function()
			{
				return arguments[1].toUpperCase();
			});
		},
		isBlank: function()
		{
			return /^\s*$/.test(this);
		}
	});

	window.Array.extend({
		each: function(f)
		{
			if (typeof f === "function" && this.length > 0) for (var i = 0; i < this.length; i++)
				f.call(this, i, this[i]);
		},
		clear: function()
		{
			this.length = 0;
		},
		insertAt: function(index, obj)
		{
			this.splice(index, 0, obj);
		},
		removeAt: function(index)
		{
			if (index >= 0) this.splice(index, 1);
		},
		indexOf: function(obj)
		{
			for (var i = 0; i < this.length; i++)
			{
				if (this[i] == obj) return i;
			}
			return -1;
		},
		contains: function(obj)
		{
			return this.indexOf(obj) >= 0;
		},
		remove: function(obj)
		{
			this.removeAt(this.indexOf(obj));
		},
		reduce: function(callback, initialValue)
		{
			var previous = initialValue, k = 0, length = this.length;
			if (typeof initialValue === "undefined")
			{
				previous = this[0];
				k = 1;
			}

			if (typeof callback === "function")
			{
				for (k; k < length; k++)
				{
					this.hasOwnProperty(k) && (previous = callback(previous, this[k], k, this));
				}
			}
			return previous;
		}
	});
	window.Array.isArray = function(v)
	{
		return v && typeof v.length == "number" && typeof v.splice == "function";
	};
	window.Date.isDate = function(v)
	{
		return v && typeof v.getFullYear == "function";
	};

	this.VERSION = "1.1.2";
	this.CLASS_NAME_REG_EXP = /^[A-Z]+[a-zA-Z]*$/;
	this.BELONG_TO_REG_EXP = /^([a-z]+(\.[a-z]+))*(\.*[A-Z]+[a-zA-Z]*)*$/;
	this.PACKAGE_NAME_REG_EXP = /^[a-z]+(\.[a-z]+)*$/;
	this.BASE_SOURCE_KEY = "pers.linhai.nature";
	this.agent = window.navigator.userAgent.toLowerCase();
	this.doc = window.document;
	this.isStrict = this.doc.compatMode === "CSS1Compat";
	this.head = this.doc.head || this.doc.getElementsByTagName("head").item(0) || this.doc.documentElement;
	this.Node = {
		ELEMENT_NODE: 1,
		ATTRIBUTE_NODE: 2,
		TEXT_NODE: 3,
		CDATA_SECTION_NODE: 4,
		ENTITY_REFERENCE_NODE: 5,
		ENTITY_NODE: 6,
		PROCESSING_INSTRUCTION_NODE: 7,
		COMMENT_NODE: 8,
		DOCUMENT_NODE: 9,
		DOCUMENT_TYPE_NODE: 10,
		DOCUMENT_FRAGEMENT_NODE: 11,
		NOTATION_NODE: 12
	};
	this.classMap = {};
	this.classpathMap = {};
	this.baseClasspathInfo = {
		"name": "baseClasspath",
		"classpath": "./classes",
		"resourcesPath": "./resources",
		"classes": [
			"pers.linhai.nature.form.FormElement", 
			"pers.linhai.nature.form.IconButton", 
			"pers.linhai.nature.form.Select", 
			"pers.linhai.nature.form.Text", 
			"pers.linhai.nature.grid.GridTable",
			"pers.linhai.nature.util.BubbleTip", 
			"pers.linhai.nature.util.Processor"
		]
	};
	this.addClasspath = function(classpathObj)
	{
		if (!classpathObj || !(classpathObj.classpath))
		{
			throw new Error("classpath attr can't be empty!");
		}

		if (!(classpathObj.name))
		{
			throw new Error("classpath'name can't be empty!");
		}
		var __classpath = classpathObj.classpath.startsWith("/") ? classpathObj.classpath : Nature.baseInfo.basePath + classpathObj.classpath;
		var _classpath = __classpath.endsWith("/") ? __classpath : __classpath + "/";

		if (classpathObj.resourcesPath)
		{
			classpathObj.resourcesPath = _classpath + "../resources/";
		}
		var __resourcesPath = classpathObj.resourcesPath.startsWith("/") ? classpathObj.resourcesPath : Nature.baseInfo.basePath + classpathObj.resourcesPath;
		var _resourcesPath = __resourcesPath.endsWith("/") ? __resourcesPath : __resourcesPath + "/";
		var classpathInfo = {
			classpath: _classpath,
			resourcesPath: _resourcesPath
		};

		if (this[classpathObj.name])
		{
			throw new Error("classpath name exists, it can't be repeated!");
		}
		else
		{
			this.classpathMap[classpathObj.name] = classpathInfo;
		}

		var classes = classpathObj.classes;
		for (var i = 0; i < classes.length; i++)
		{
			this.classMap[classes[i]] = classpathInfo;
		}
	};
	this.getClasspath = function(name)
	{
		return this.classpathMap[name];
	};
	this.setDebug = function(_d)
	{
		this._debug = _d == true;
	};
	this.log = function(msg)
	{
		if (window.console && this._debug) window.console.log(msg);
	};
	this.checkType = function(v, t, f)
	{
		if (typeof v != t) throw new Error("The type of the param in the method '" + f + "' must be '" + t + "'.");
	};
	this.checkReference = function(v, c, f)
	{
		if (!(v instanceof c)) throw new Error("The the param in the method '" + f + "' must be a instance of the Class[" + c + "].");
	};
	this.namespace = function(namespace)
	{
		return (namespace || "").split("\.").reduce(function(space, name)
		{
			return space[name] || (space[name] = {
				namespace: Nature.namespace
			});
		}, window);
	};
	this.eventUtil = {
		addListener: function(element, type, handler)
		{
			type = this.formatEventName(type);
			if (element.addEventListener) element.addEventListener(type, handler, false);
			else if (element.attachEvent) element.attachEvent("on" + type, handler);
			else element["on" + type] = handler;
		},
		removeListener: function(element, type, handler)
		{
			type = this.formatEventName(type);
			if (element.removeEventListener) element.removeEventListener(type, handler, false);
			else if (element.detachEvent) element.detachEvent("on" + type, handler);
			else element["on" + type] = null;
		},
		formatEventName: function(en)
		{
			return en.startsWith("on") ? en.substring(2) : en;
		}
	};

	var readyCallbackCache = [];
	function docOnLoaded()
	{
		if (Nature.isReady == 1 || Nature.isReady == 2)
		{
			return;
		}
		Nature.isReady = 1;

		// 将框架自身类路径加入系统
		Nature.addClasspath(Nature.baseClasspathInfo);

		// 导入Nature.css主框架样式
		Nature.CSSLoader.load(Nature.getClasspath("baseClasspath").resourcesPath + "/style/Nature.css", function()
		{
			Nature.isReady = 2;

			var _IE = (function()
			{
				var v = 3, div = Nature.doc.createElement("div");
				while(div.innerHTML = "<!--[if gt IE " + (++v) + "]><i></i><![endif]-->", div.getElementsByTagName("i")[0])
					;
				return v > 4 ? v : false;
			})();

			// 不支持IE7及已下版本
			if ((_IE && _IE < 8) || (Nature.doc.documentMode && Nature.doc.documentMode < 8))
			{
				var err_msg = "Note that this framework does not support IE7 and the following versions of the browser!";
				new Nature.Mask({
					message: err_msg,
					renderTo: Nature.getBody()
				});
				throw new Error(err_msg);
			}

			function _exe_cbs()
			{
				var _cb = undefined;
				while((_cb = readyCallbackCache.pop()) != undefined)
					Nature.ready(_cb);
			}

			if (Nature.baseInfo.imports)
			{
				Nature.ClassLoader.load(Nature.baseInfo.imports.split(/ *, */), _exe_cbs, Nature.baseInfo.showMask === "true");
			}
			else _exe_cbs();
		});
	}
	this.eventUtil.addListener(this.doc, "DOMContentLoaded", docOnLoaded);
	this.eventUtil.addListener(this.doc, "readystatechange", docOnLoaded);
	this.eventUtil.addListener(window, "load", docOnLoaded);
	this.ready = function(_cb)
	{
		if (!(_cb instanceof Function)) throw new Error("In the function Nature.ready, the type of the '_cb' param must be Function.");
		if (this.isReady != 2)
		{
			if (!readyCallbackCache.contains(_cb))
			{
				readyCallbackCache.push(_cb);
			}
		}
		else _cb.call(window);
	};
	this.onFrameReady = function(frame, f)
	{
		if (!(f instanceof Function)) throw new Error("In the function Nature.onFrameReady, the type of the 'f' param must be Function.");
		if (!frame) throw new Error("frame is invalid!");
		this.eventUtil.addListener(frame, "load", f);
	};
	this.createDom = function(tagName, class_name)
	{
		var dom = this.doc.createElement(tagName.toUpperCase());
		if (typeof class_name == "string") dom.className = class_name;
		return dom;
	};
	this.getDom = function(el)
	{
		if (!el || !this.doc) return null;
		return typeof el == "string" ? this.doc.getElementById(el) : el;
	};
	this.createFragment = function()
	{
		return this.doc.createDocumentFragment();
	};
	this.createTextNode = function(_t)
	{
		return this.doc.createTextNode(_t);
	};
	this.getDocumentElement = function()
	{
		return this.isStrict ? this.doc.documentElement || this.doc.body : this.doc.body;
	};
	this.getBody = function()
	{
		return this.doc.body || this.doc.documentElement;
	};
	this.getDocumentScroll = function()
	{
		return {
			left: window.pageXOffset || this.doc.documentElement.scrollLeft || (this.doc.body.scrollLeft || 0),
			top: window.pageYOffset || this.doc.documentElement.scrollTop || (this.doc.body.scrollTop || 0)
		}
	};
	this.getDocumentHeight = function()
	{
		return Math.max(this.getDocumentElement().scrollHeight, this.getViewHeight());
	};
	this.getDocumentWidth = function()
	{
		return Math.max(this.getDocumentElement(), this.getViewWidth());
	};
	this.getViewHeight = function()
	{
		return this.getDocumentElement().clientHeight;
	};
	this.getViewWidth = function()
	{
		return this.getDocumentElement().clientWidth;
	};
	this.remove = function(n)
	{
		if (n instanceof Nature.DOMElement) n = n.dom;
		if (n && n.parentNode && n.tagName != "BODY") n.parentNode.removeChild(n);
	};
	this.cssProp = function(attr)
	{
		if (attr === "float")
		{
			var ca = this.cssAttr("styleFloat");
			if (ca.supports) return ca;
			ca = this.cssAttr("cssFloat");
			if (ca.supports) return ca;
		}
		var attrObj = {
			supports: false
		}, attr_name = attr.camelCase();
		if (attr_name in this.getBody().style)
		{
			attrObj.name = attr_name;
			attrObj.supports = true;
			return attrObj;
		}
		var prefixs = ["webkit", "moz", "ms", "o"];
		for (var i = 0; i < prefixs.length; i++)
		{
			if ((attr_name = (prefixs[i] + "-" + attr).camelCase()) in this.getBody().style)
			{
				attrObj.name = attr_name;
				attrObj.supports = true;
				return attrObj;
			}
		}
		return attrObj;
	};

	// abstract class
	this.AbstractObject = function(obj)
	{
		// make self extend the first argument whose type is object
		if (typeof obj === "object" && !("nodeType" in obj)) Nature.extend(this, obj);
	}

	/**
	 * 创建类（万物）的方法
	 */
	this.create = function(_attrs)
	{
		// 类型为Object的参数
		Nature.checkType(_attrs, "object", "Nature.create");

		var _class_name = null, _constructor = null;
		if (_attrs.hasOwnProperty("className"))
		{
			if (this.CLASS_NAME_REG_EXP.test(_attrs.className))
			{
				_class_name = _attrs.className;
				_constructor = _attrs[_class_name];
				if (_constructor != undefined && !(_constructor instanceof window.Function)) throw new Error("The Constructor of the Class[" + _class_name + "] must be a function.");
				var _self_class = _constructor;
				_constructor = function()
				{
					// 调用父类构造函数
					if (_constructor._super_class) _constructor._super_class.apply(this, arguments);

					// 调用自身构造函数
					_constructor._self_class.apply(this, arguments);
				};
				_constructor._self_class = _self_class;
				delete _attrs[_class_name];
				delete _attrs.className;
			}
			else throw new Error("The class name naming rules: Capitalize the first letter of the word, the rest of the lowercase");
		}
		else throw new Error("The className can't be empty!");

		var _package_name = "", _package_obj = window;
		if (_attrs.hasOwnProperty("belongs") && this.BELONG_TO_REG_EXP.test(_attrs.belongs))
		{
			_package_obj = new Function("try{return this." + _attrs.belongs + ";}catch(e){return undefined;}").call(window);
			Nature.checkType(_package_obj, "function", "Nature.create@belongs");
		}
		else if (_attrs.hasOwnProperty("packages"))
		{
			var __package_name = _attrs.packages;
			if (this.PACKAGE_NAME_REG_EXP.test(__package_name))
			{
				_package_obj = this.namespace(__package_name);
				_package_name = __package_name;
				delete _attrs.packages;
			}
			else throw new Error("The package name must consist of a lowercase letters of the English");
		}

		// 定义一个类，执行这句话，是为构造函数命名，让构造函数为非匿名函数
		var _class = _package_obj[_class_name] = _constructor;

		// 通过类名调用getName方法，返回类路径名;getSimpleName 返回类名
		_class.getName = new Function("return '" + _package_name + "." + _class_name + "';");
		_class.getSimpleName = new Function("return '" + _class_name + "';");

		if (_attrs.hasOwnProperty("statics") && typeof _attrs.statics == "object")
		{
			// 处理静态属性
			var _attr_value = _attrs.statics;
			for ( var _$attr in _attr_value)
			{
				if (_attr_value.hasOwnProperty(_$attr)) _class[_$attr] = _attr_value[_$attr];
			}
			delete _attrs.statics;
		}

		var classesNeedToImport = [];
		if (_attrs.hasOwnProperty("imports"))
		{
			// 处理依赖
			classesNeedToImport = _attrs.imports;
			delete _attrs.imports;
		}

		// 定义导入完成后的回调函数
		var _call_back = function()
		{
			// 处理继承任务
			if (_call_back.extendTask instanceof window.Function) _call_back.extendTask();

			// 处理导入完成后的结束任务
			if (_call_back.finishTask instanceof window.Function) _call_back.finishTask();
		};

		_class._call_back = _call_back;

		if (!_attrs.hasOwnProperty("extend"))
		{
			_attrs["extend"] = Nature.AbstractObject;
		}
		if (_attrs.hasOwnProperty("extend"))
		{
			var ex = _attrs.extend;
			delete _attrs.extend;
			if (typeof ex === "string")
			{
				var super_class = new Function("try{return this." + ex + ";}catch(e){return undefined;}").call(window);
				if (typeof super_class == "function") ex = super_class;
			}

			if (!("function|string".contains(typeof ex))) throw new Error("Supper class[" + ex + "] to be extended by the [" + _class.getName() + "] is incorrect, please check!");
			if (typeof ex == "string") classesNeedToImport.push(ex);

			// 该方案解决了A-extend->B-extend->C的场景，深入利用原型继承的原理实现
			var extendTask = function()
			{
				var supperClass = typeof extendTask.supperClass == "function" ? extendTask.supperClass : new Function("return this." + extendTask.supperClass + ";").call(window);
				extendTask.selfClass.extend(supperClass);
				extendTask.selfClass.extend(extendTask.selfClassAttrs);
			};

			extendTask.selfClass = _class;
			extendTask.supperClass = ex;
			extendTask.selfClassAttrs = _attrs;

			// 注册继承任务
			_class._call_back.extendTask = extendTask;
		}

		if (classesNeedToImport.length == 0)
		{
			_class._call_back();
			_class._loadFinished = true;
		}
		else this.ClassLoader.load(classesNeedToImport, _class._call_back);
	};

	// 设置框架运行根目录以及类所在根目录classes
	this.baseInfo = (function()
	{
		var jScripts = this.doc.scripts || this.doc.getElementsByTagName("script"), cur_import_script = jScripts[jScripts.length - 1], _cur_script_src = cur_import_script.src, _path = _cur_script_src
				.substring(0, _cur_script_src.lastIndexOf("Nature.js")), _host = window.location.host, _indexOfHost = _path.indexOf(_host);
		var s_dataset = cur_import_script.dataset, imports, showMask;
		if (s_dataset)
		{
			imports = s_dataset["imports"];
			showMask = s_dataset["showMask"];
		}
		else
		{
			if (cur_import_script.attributes["data-imports"]) imports = cur_import_script.attributes["data-imports"].nodeValue;
			if (cur_import_script.attributes["data-show-mask"]) showMask = cur_import_script.attributes["data-show-mask"].nodeValue;
		}
		var baseInfo = {};
		if (imports) baseInfo.imports = imports;
		if (showMask) baseInfo.showMask = showMask;
		baseInfo.basePath = _indexOfHost != -1 ? _path.substring(_indexOfHost + _host.length) : _path;
		return baseInfo;
	}).call(Nature);

	this.initialization = true;
};

Nature.apply(Nature);

/**
 * 类加载器
 * 
 * @_class_list 待带入的类数组
 * @_call_back 带入完成待执行的回调函数
 * @_is_show_mask 是否在导入时显示遮层
 */
Nature.create({
	belongs: "Nature",
	className: "ClassLoader",
	ClassLoader: function(_obj)
	{
		// 重复性class导入验证的hashMap
		this._repetitive_import_validation_table = new Nature.HashTable();
		this._class_total = this._class_list.length;
		this._class_import_index = 0;

		// 处理导入是否显示面具
		if (this._is_show_mask === true)
		{
			this._mask = new Nature.Mask({
				message: 'Please wait, while the classes are in loading...',
				bgColor: "#FFFFFF",
				renderTo: Nature.getBody()
			});
		}
	},
	_load_class: function()
	{
		var _class_name = this._class_list[this._class_import_index];

		// 如果该类已被加入载入队列，则返回进行下一个类载入处理
		if (this._repetitive_import_validation_table.containsKey(_class_name))
		{
			Nature.log(_class_name + "类正在被载入，请勿重复载入!");
			this._load_next();
			return;
		}

		this._repetitive_import_validation_table.put(_class_name, _class_name);

		var _class = new Function("try{return this." + _class_name + ";}catch(e){return undefined;}").call(window);

		// 如果该类已被载入，则返回进行下一个类载入处理
		if (typeof _class == "function")
		{
			Nature.log(_class_name + "类已被载入，进行下一个类载入处理:" + _classes[this._class_import_index + 1]);
			this._load_next();
			return;
		}

		this._set_mask_msg("Please wait, the class ' " + _class_name + " ' is in loading...");

		// 查找该类对应的路径资源信息，以便进行加载
		var _source = Nature.classMap[_class_name], _this = this;

		if (!_source)
		{
			throw new Error("ClassNotFoundException: " + _class_name);
		}

		Nature.ScriptLoader.load(this._to_visit_path(_source.classpath, _class_name), function()
		{
			var _cc = new Function("try{return this." + _class_name + ";}catch(e){return undefined;}").call(window);
			if (_cc == undefined) throw new Error("Import the class '" + _class_name + "' failed!");

			var load_css_and_next_task = function()
			{
				delete load_css_and_next_task.curImportClass._loadFinished;
				delete load_css_and_next_task.curImportClass._call_back;

				// 如果该类存在有与之对应的css文件，则进行导入
				var cssUrl = load_css_and_next_task.classSource.resourcesPath + "style/" + load_css_and_next_task.curImportClass.getName() + ".css";
				Nature.CSSLoader.load(cssUrl, load_css_and_next_task.classLoader._load_next.bind(load_css_and_next_task.classLoader));
			};

			load_css_and_next_task.classLoader = _this;
			load_css_and_next_task.curImportClass = _cc;
			load_css_and_next_task.classSource = _source;

			if (_cc._loadFinished != true) _cc._call_back.finishTask = load_css_and_next_task;
			else load_css_and_next_task();
		});
	},
	_load_next: function()
	{
		this._class_import_index++;
		if (this._class_import_index < this._class_total) this._load_class();
		else if (this._class_total == this._class_import_index) this._complete();
	},
	_complete: function()
	{
		this._remove_mask();

		// 真正导入结束
		Nature.ready(this._call_back);
	},
	_get_source: function(str, _class_name)
	{
		var _dot_index = str.lastIndexOf("."), _source_name = undefined, _source = undefined;
		_source_name = _dot_index != -1 ? str.substring(0, _dot_index) : str;
		_source = Nature.sourceMap[_source_name];
		if (_dot_index == -1 && _source == undefined) throw new Error("Can't find the source you registered by the className：" + _class_name);
		return typeof _source == "object" ? _source : this._get_source(_source_name, _class_name);
	},
	_to_visit_path: function(_class_path, _class_name)
	{
		return _class_path + _class_name.replaceAll("\\.", "/") + ".js";
	},
	_set_mask_msg: function(_msg)
	{
		if (this._mask instanceof Nature.Mask) this._mask.setMessage(_msg);
	},
	_remove_mask: function()
	{
		if (this._mask instanceof Nature.Mask) this._mask.remove();
	},
	statics: {
		load: function(_class_list, _call_back, _is_show_mask)
		{
			var params = {
				_class_list: Array.isArray(_class_list) ? _class_list : [_class_list],
				_call_back: _call_back,
				_is_show_mask: _is_show_mask
			};
			this.newInstance.call(this, params)._load_class();
		}
	}
});

/**
 * dom元素扩展
 */
Nature.create({
	belongs: "Nature",
	className: "DOMElement",
	DOMElement: function(dom)
	{
		this.dom = Nature.getDom(dom);
		this._listeners = {};
	},
	attr: function()
	{
		if (arguments.length == 0) throw new Error("The length of the arguments in the method 'Nature.DOMElement.attr' must be one or two.");
		var fa = arguments[0];
		if (typeof fa == "string")
		{
			if (arguments.length == 1) return this.dom.getAttribute(fa);
			else if (arguments.length == 2)
			{
				this.dom.setAttribute(fa, arguments[1]);
				return this;
			}
		}
		else if (typeof fa == "object" && arguments.length == 1)
		{
			for ( var k in fa)
			{
				if (fa.hasOwnProperty(k)) this.attr(k, fa[k]);
			}
			return this;
		}
		else throw new Error("The length of the arguments in the method 'Nature.DOMElement.attr' must be one or two.");
	},
	prop: function()
	{
		if (arguments.length == 0) throw new Error("The length of the arguments in the method 'Nature.DOMElement.prop' must be one or two.");
		var fa = arguments[0];
		if (typeof fa == "string")
		{
			if (arguments.length == 1) return this.dom[fa];
			else if (arguments.length == 2)
			{
				this.dom[fa] = arguments[1];
				return this;
			}
		}
		else if (typeof fa == "object" && arguments.length == 1)
		{
			for ( var k in fa)
			{
				if (fa.hasOwnProperty(k)) this.prop(k, fa[k]);
			}
			return this;
		}
		else throw new Error("The length of the arguments in the method 'Nature.DOMElement.prop' must be one or two.");
	},
	append: function(_c)
	{
		if (this.dom.nodeType != Nature.Node.ELEMENT_NODE) return this;
		if (_c instanceof Nature.DOMElement) _c = _c.dom;
		var _child = null;
		if (typeof _c == "object" && (_c.nodeType == Nature.Node.ELEMENT_NODE || _c.nodeType == Nature.Node.DOCUMENT_FRAGEMENT_NODE)) _child = _c;
		else if (typeof _c == "string") _child = Nature.createTextNode(_c);
		if (_child != null) this.dom.appendChild(_child);
		return this;
	},
	hasAttr: function(attr)
	{
		return(attr in this.dom.attributes);
	},
	hasProp: function(prop)
	{
		return(prop in this.dom);
	},
	removeAttr: function(attr)
	{
		this.dom.removeAttribute(attr);
		return this;
	},
	removeProp: function(prop)
	{
		delete this.dom[prop];
		return this;
	},
	hide: function()
	{
		return this.style("display", "none");
	},
	show: function()
	{
		return this.style("display", "block");
	},
	html: function()
	{
		if (arguments.length == 1) return this.prop("innerHTML", arguments[0]);
		else if (arguments.length == 0) return this.dom.innerHTML;
	},
	getValue: function()
	{
		return this.dom.value;
	},
	setValue: function()
	{
		return this.prop("value", arguments[0]);
	},
	getWidth: function()
	{
		return this.dom.offsetWidth || window.parseInt(this.style("width"));
	},
	setWidth: function(w)
	{
		if (typeof w == "number" && w >= 0) return this.style("width", w + "px");
	},
	getHeight: function()
	{
		return this.dom.offsetHeight || window.parseInt(this.style("height"));
	},
	setHeight: function(h)
	{
		if (typeof h == "number" && h >= 0) return this.style("height", h + "px");
	},
	enable: function()
	{
		return this.prop("disabled", false);
	},
	disable: function()
	{
		return this.prop("disabled", true);
	},
	focus: function()
	{
		try
		{
			this.dom.focus();
		}
		catch(e)
		{
		}
		return this;
	},
	blur: function()
	{
		try
		{
			this.dom.blur();
		}
		catch(e)
		{
		}
		return this;
	},
	addClass: function(className)
	{
		if (typeof className != "string") return this;
		if (className && !this.hasClass(className)) this.dom.className = this.dom.className + " " + className;
		return this;
	},
	removeClass: function(className)
	{
		if (!className || !this.dom.className) return this;
		if (this.hasClass(className))
		{
			var re = new RegExp("(?:^|\\s+)" + className + "(?:\\s+|$)", "g");
			this.dom.className = this.dom.className.replace(re, " ");
		}
		return this;
	},
	hasClass: function(className)
	{
		return className && (" " + this.dom.className + " ").contains(" " + className + " ");
	},
	replaceClass: function(oldClassName, newClassName)
	{
		return this.removeClass(oldClassName).addClass(newClassName);
	},
	style: function()
	{
		if (arguments.length == 0) throw new Error("The method 'Nature.DOMElement.style' must have at least one param.");
		var prop = arguments[0];
		if (typeof prop == "string")
		{
			prop = Nature.cssProp(prop).name;
			if (arguments.length == 1)
			{
				var _v, cs, el = this.dom;
				if (_v = el.style[prop]) return _v;
				if (window.getComputedStyle) return window.getComputedStyle(el, null)[prop];
				if (document.defaultView && document.defaultView.getComputedStyle) return document.defaultView.getComputedStyle(el, null)[prop];
				if (cs = el.currentStyle) return cs[prop];
				return null;
			}
			else if (arguments.length == 2)
			{
				var v = arguments[1];
				if (prop == "opacity") return this.setOpacity(v);
				this.dom.style[prop] = v;
				return this;
			}
		}
		else if (typeof prop == "object" && arguments.length == 1)
		{
			for ( var k in prop)
			{
				if (prop.hasOwnProperty(k)) this.style(k, prop[k]);
			}
			return this;
		}
		else throw new Error("The method 'Nature.DOMElement.style' received an error type param.");
	},
	setOpacity: function(opacity)
	{
		var s = this.dom.style;
		if (Nature.cssProp("opacity").supports) s.opacity = opacity / 100;
		else
		{
			s.zoom = 1;
			s.filter = "alpha(opacity=" + opacity + ")";
		}
		return this;
	},
	position: function(l, t, r, b)
	{
		if (arguments.length > 0)
		{
			if (typeof l == "number") this.style("left", l + "px");
			if (typeof t == "number") this.style("top", t + "px");
			if (typeof r == "number") this.style("right", r + "px");
			if (typeof b == "number") this.style("bottom", b + "px");
			return this;
		}
		var domEl = this.dom, B = Nature.getBody();
		if (domEl == B) return {
			x: 0,
			y: 0
		};
		if (domEl.getBoundingClientRect)
		{
			var M = domEl.getBoundingClientRect(), N = Nature.getDocumentScroll();
			return {
				x: (M.left + N.left),
				y: (M.top + N.top)
			};
		}
		var X = 0, Y = 0, F = domEl;
		while(F)
		{
			X += F.offsetLeft;
			Y += F.offsetTop;
			F = F.offsetParent;
		}
		return {
			x: X,
			y: Y
		};
	},
	edgeDistance: function()
	{
		var pos = this.position();
		var x = pos.x, y = pos.y, w = this.getWidth(), h = this.getHeight();
		var documentScroll = Nature.getDocumentScroll();
		var viewWidth = Nature.getViewWidth(), viewHeight = Nature.getViewHeight();
		var d_left, d_top, d_right, d_bottom;
		d_left = x - documentScroll.left;
		d_right = viewWidth - d_left - w;
		d_top = y - documentScroll.top;
		d_bottom = viewHeight - d_top - h;
		return {
			left: d_left,
			top: d_top,
			right: d_right,
			bottom: d_bottom
		};
	},
	bind: function(en, f)
	{
		en = Nature.eventUtil.formatEventName(en);
		this._cache_listener(en, f);
		Nature.eventUtil.addListener(this.dom, en, f);
		return this;
	},
	unBind: function(en, f)
	{
		en = Nature.eventUtil.formatEventName(en);
		this._remove_cache_listener(en, f);
		Nature.eventUtil.removeListener(this.dom, en, f);
		return this;
	},
	unBindAll: function()
	{
		var _listeners = this._listeners, list = null;
		for ( var en in _listeners)
		{
			if (_listeners.hasOwnProperty(en))
			{
				list = _listeners[en];
				for (var i = 0; i < list.length; i++)
					this.unBind(en, list[i]);
			}
		}
		return this;
	},
	_get_listener_list: function(en)
	{
		var list = this._listeners[en];
		if (!Array.isArray(list))
		{
			list = [];
			this._listeners[en] = list;
		}
		return list;
	},
	_remove_cache_listener: function(en, f)
	{
		this._get_listener_list(en).remove(f);
	},
	_cache_listener: function(en, f)
	{
		this._remove_cache_listener();
		this._get_listener_list(en).push(f);
	},
	render: function(container)
	{
		var container_dom = (container instanceof Nature.DOMElement) ? container.dom : Nature.getDom(container);
		if (container_dom) container_dom.appendChild(this.dom);
		return this;
	}
});

/**
 * CSS加载器,参数类型
 */
Nature.create({
	belongs: "Nature",
	className: "CSSLoader",
	CSSLoader: function(_url_list, _call_back)
	{
		this.url_list = Array.isArray(_url_list) ? _url_list : [_url_list];
		if (typeof _call_back == "function") this.call_back = _call_back;
		this.load_index = 0;
	},
	_load: function()
	{
		this.cssEl = new Nature.DOMElement(Nature.createDom("link"));
		this.cssEl.attr({
			type: "text/css",
			rel: "stylesheet",
			href: this.url_list[this.load_index]
		});
		var a = this.cssEl.hasProp("onload");
		var b = this.cssEl.hasProp("onerror");
		if (a) this.cssEl.bind("onload", this._onload.bind(this));
		if (b) this.cssEl.bind("onerror", this._onerror.bind(this));
		this.cssEl.render(Nature.head);
		if (!a) this._onload();
	},
	_onerror: function()
	{
		Nature.remove(this.cssEl);
		this.cssEl.unBindAll();
		delete this.cssEl;
		if (typeof this.call_back == "function") this.call_back();
	},
	_onload: function()
	{
		this.load_index++;
		this.cssEl.unBindAll();
		if (this.load_index < this.url_list.length) this._load();
		else
		{
			delete this.cssEl;
			if (typeof this.call_back == "function") this.call_back();
		}
	},
	statics: {
		load: function()
		{
			this.newInstance.apply(this, arguments)._load();
		}
	}
});

/**
 * Script加载器,参数类型
 */
Nature.create({
	belongs: "Nature",
	className: "ScriptLoader",
	ScriptLoader: function(_url_list, _call_back)
	{
		this.url_list = Array.isArray(_url_list) ? _url_list : [_url_list];
		if (typeof _call_back == "function") this.call_back = _call_back;
		this.load_index = 0;
	},
	_load: function()
	{
		this.scriptEl = new Nature.DOMElement(Nature.createDom("script"));
		this.scriptEl.attr({
			type: "text/javascript",
			src: this.url_list[this.load_index]
		});
		var a = this.scriptEl.hasProp("onload");
		if (a) this.scriptEl.bind("onload", this._onload.bind(this));
		if (!a)
		{
			a = this.scriptEl.hasProp("readyState");
			if (a) this.scriptEl.bind("onreadystatechange", this._onload.bind(this));
		}
		this.scriptEl.render(Nature.head);
		if (!a) this._onload();
	},
	_onload: function()
	{
		this.load_index++;
		Nature.remove(this.scriptEl);
		this.scriptEl.unBindAll();
		if (this.load_index < this.url_list.length) this._load();
		else
		{
			delete this.scriptEl;
			if (typeof this.call_back == "function") this.call_back();
		}
	},
	statics: {
		load: function()
		{
			this.newInstance.apply(this, arguments)._load();
		}
	}
});

/**
 * 哈希表HashTable
 */
Nature.create({
	belongs: "Nature",
	className: "HashTable",
	HashTable: function()
	{
		this.clear();
	},
	containsKey: function(key)
	{
		return this._hash.hasOwnProperty(key);
	},
	remove: function(key)
	{
		if (delete this._hash[key]) this._count--;
	},
	clear: function()
	{
		this._hash = {};
		this._count = 0;
	},
	get: function(key)
	{
		return this._hash[key];
	},
	size: function()
	{
		return this._count;
	},
	isEmpty: function()
	{
		return this._count <= 0;
	},
	put: function(key, value)
	{
		if (!(this.containsKey(key))) this._count++;
		this._hash[key] = value;
	}
});

/**
 * Ajax类
 * 
 * @url 请求路径
 * @success 响应成功时回调函数
 * @failure 响应失败时回调函数
 * @method 请求方法post或get 默认为POST
 * @timeout: 5
 */
Nature.create({
	belongs: "Nature",
	className: "Ajax",
	method: "GET",
	timeout: 30,
	Ajax: function()
	{
	},
	_default_headers: {
		"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
		"Accept": "application/json, text/javascript, text/html, application/xml, text/xml, */*",
		"Cache-Control": "no-cache",
		"If-Modified-Since": "0",
		"X-Requested-With": "XMLHttpRequest"
	},
	_setHeaders: function()
	{
		var _headers = this.headers;
		if (typeof _headers == "object")
		{
			for ( var k in this._default_headers)
			{
				if (!_headers.hasOwnProperty(k)) _headers[k] = this._default_headers[k];
			}
			for ( var name in _headers)
			{
				if (_headers.hasOwnProperty(name)) this._xhr.setRequestHeader(name, _headers[name]);
			}
		}
		else
		{
			for ( var k in this._default_headers)
			{
				if (this._default_headers.hasOwnProperty(k)) this._xhr.setRequestHeader(k, this._default_headers[k]);
			}
		}
	},
	_parse_params: function()
	{
		var paramArray = new Array();
		var _params = this.params;
		if (typeof _params == "object")
		{
			for ( var name in _params)
			{
				if (_params.hasOwnProperty(name)) paramArray.push(encodeURIComponent(name) + "=" + encodeURIComponent(_params[name]));
			}
		}
		return paramArray.length == 0 ? null : paramArray.join("&");
	},
	_create_xhr: function()
	{
		try
		{
			this._xhr = new window.XMLHttpRequest();
		}
		catch(e)
		{
			this._xhr = new window.ActiveXObject("Microsoft.XMLHTTP");
		}
		finally
		{
			if (this._xhr == null) throw new Error("Create ajax xhr failed.");
			this._xhr.onreadystatechange = this._onreadystatechange.bind(this);
		}
	},
	_handle_timeout: function()
	{
		this._timeout_timer = window.setTimeout(this._abort.bind(this), ((this.timeout == "number" && this.timeout > 0) ? this.timeout : 30) * 1000);
	},
	_abort: function()
	{
		try
		{
			this._xhr.abort();
		}
		catch(e)
		{
		}
		finally
		{
			delete this._xhr;
			throw new Error("The page :" + this.url + " requests timeout.");
		}
	},
	_onreadystatechange: function()
	{
		if (this._xhr && this._xhr.readyState == 4) this._oncomplete();
	},
	_oncomplete: function()
	{
		if (this._timeout_timer != undefined)
		{
			window.clearTimeout(this._timeout_timer);
			delete this._timeout_timer;
		}
		var _status = this._xhr.status;
		if (((_status >= 200 && _status < 300) || _status == 304) && typeof this.success == "function")
		{
			try
			{
				this.success(this._xhr);
			}
			catch(e)
			{
				var errorMsg = "\nFile url requested by ajax: " + this.url;
				e.description = ((e.description ? e.description : "") + errorMsg);
				e.message = ((e.message ? e.message : "") + errorMsg);
				throw e;
			}
		}
		else
		{
			var _error_msg = this._error_msg_map["status_" + _status];
			if (_error_msg == undefined) _error_msg = "The url address request failed";
			_error_msg += (", errorCode: " + _status + ", url: " + this.url);
			if (typeof this.failure == "function") this.failure(_error_msg);
			else throw new Error(_error_msg);
		}

		// 需要考虑_xhr_callback为undefined的情况
		delete this._xhr.onreadystatechange;
	},
	_error_msg_map: {
		status_0: "The requested url is not reachable", // 请求地址无效
		status_12029: "The requested url is not reachable", // 请求地址无效
		status_403: "The requested url access forbidden", // 你请求的页面禁止访问
		status_404: "The requested url does not exist", // 你请求的页面不存在
		status_500: "The requested url generated Web server internal error", // 请求页面产生Web服务器内部错误
		status_502: "The Web server received an invalid response", // Web服务器收到无效的响应
		status_503: "Server is busy or unavailable, please try again later" // 服务器繁忙，请稍后再试
	},
	_request_method: {
		HEAD: "HEAD", // 向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息。
		GET: "GET", // 向特定的资源发出请求。
		POST: "POST", // 向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。POST请求可能会导致新的资源的创建和/或已有资源的修改。
		PUT: "PUT", // 向指定资源位置上传其最新内容。
		DELETE: "DELETE" // 请求服务器删除Request-URI所标识的资源。
	},
	request: function()
	{
		try
		{
			this._create_xhr();
			var _method = this._request_method[this.method.toUpperCase()];
			_method = _method ? _method : this._request_method.POST;
			var params = this._parse_params();
			if (_method == this._request_method.GET) this.url += (params == null ? "" : ("?" + params));
			this._xhr.open(_method, this.url, this.async != false);
			this._handle_timeout();
			this._setHeaders();
			this._xhr.send(_method == this._request_method.GET ? null : params);
		}
		catch(e)
		{
			if (typeof this.failure == "function") this.failure("The request address: " + this.url + " " + e.description);
			else throw e;
		}
	},
	statics: {
		request: function(obj)
		{
			Nature.checkType(obj, "object", "Nature.Ajax.request");
			this.newInstance.apply(this, arguments).request();
		}
	}
});

Nature.create({
	belongs: "Nature",
	className: 'Mask',
	Mask: function()
	{
		this.container_div = Nature.createDom('div', 'mask_container');
		this._table_info = this._create_table();
		this.container_div.appendChild(this._table_info);
		this.containerEl = new Nature.DOMElement(this.container_div);
		this.containerEl.setOpacity(100);

		this.maskRenderContainer = new Nature.DOMElement(Nature.getDom(this.renderTo));
		this.maskRenderContainerOverflow = this.maskRenderContainer.style("overflow");
		if (this.maskRenderContainerOverflow != 'hidden') this.maskRenderContainer.style("overflow", "hidden");

		// 渲染
		this.render(this.maskRenderContainer);
	},
	_create_table: function()
	{
		var _table = Nature.createDom('table', 'mask_table');
		_table.border = 0;
		_table.cellPadding = 0;
		_table.cellSpacing = 0;
		_table.align = 'center';
		var r1 = _table.insertRow(0);
		var c1 = r1.insertCell(0);
		var mask_loading_div = Nature.createDom('div', 'mask_loading');
		c1.appendChild(mask_loading_div);
		var r2 = _table.insertRow(1);
		var c2 = r2.insertCell(0);
		c2.innerHTML = this.message;
		this.contentCell = c2;
		return _table;
	},
	setMessage: function(msg)
	{
		this.contentCell.innerHTML = msg;
	},
	remove: function()
	{
		Nature.remove(this._table_info);
		Nature.Animation.monitor({
			duration: typeof this.duration == 'number' ? this.duration : 500,
			dom: this.containerEl,
			end: (function()
			{
				Nature.remove(this.container_div);
				if (this.maskRenderContainer.style("overflow") != this.maskRenderContainerOverflow) this.maskRenderContainer.style("overflow", this.maskRenderContainerOverflow);
			}).bind(this)
		});
		this.containerEl.style("background-color", "transparent");
	},
	render: function(_container)
	{
		if (_container) _container.append(this.container_div);
	}
});

/**
 * 动画类
 */
Nature.create({
	belongs: "Nature",
	className: "Animation",
	duration: 500,
	delay: 0,
	transitionEndArr: ["transitionend", "webkitTransitionEnd", "oTransitionEnd", "MSTransitionEnd"],
	property: "all",
	css3AnimationSupports: (function()
	{
		var props = ["transition-property", "transition-duration", "transition-timing-function", "transition-delay"];
		var supportInfo = {
			isSupport: true
		};
		var pro;
		for (var i = 0; i < props.length; i++)
		{
			pro = props[i];
			var css3Prop = Nature.cssProp(pro);
			if (css3Prop.supports == true)
			{
				supportInfo[pro] = css3Prop.name;
			}
			else
			{
				supportInfo.isSupport = false;
			}
		}
		return supportInfo;
	})(),
	Animation: function()
	{
		this.isEnd = false;
		this.dom = this.dom instanceof Nature.DOMElement ? this.dom : new Nature.DOMElement(this.dom);
	},
	_start: function()
	{
		if (this.css3AnimationSupports.isSupport == false)
		{
			this._stop();
		}
		else
		{
			this.dom.style(this.css3AnimationSupports["transition-property"], this.property);
			this.dom.style(this.css3AnimationSupports["transition-duration"], (this.duration / 1000) + "s");
			this.dom.style(this.css3AnimationSupports["transition-timing-function"], typeof this.effect == "undefined" ? this.constructor.CSS_EASE._default : this.effect);
			this.dom.style(this.css3AnimationSupports["transition-delay"], (this.delay / 1000) + "s");
			if (typeof this.end == 'function')
			{
				for (var i = 0; i < this.transitionEndArr.length; i++)
					this.dom.bind(this.transitionEndArr[i], this._stop.bind(this));
			}
		}
	},
	_stop: function()
	{
		if (typeof this.end == 'function' && this.isEnd == false)
		{
			this.isEnd = true;
			this.end();
			this.dom.unBindAll();
		}
	},
	statics: {
		CSS_EASE: {
			_default: "ease",
			"in": "ease-in",
			out: "ease-out",
			"in-out": "ease-in-out",
			snap: "cubic-bezier(0,1,.5,1)",
			easeInCubic: "cubic-bezier(.550,.055,.675,.190)",
			easeOutCubic: "cubic-bezier(.215,.61,.355,1)",
			easeInOutCubic: "cubic-bezier(.645,.045,.355,1)",
			easeInCirc: "cubic-bezier(.6,.04,.98,.335)",
			easeOutCirc: "cubic-bezier(.075,.82,.165,1)",
			easeInOutCirc: "cubic-bezier(.785,.135,.15,.86)",
			easeInExpo: "cubic-bezier(.95,.05,.795,.035)",
			easeOutExpo: "cubic-bezier(.19,1,.22,1)",
			easeInOutExpo: "cubic-bezier(1,0,0,1)",
			easeInQuad: "cubic-bezier(.55,.085,.68,.53)",
			easeOutQuad: "cubic-bezier(.25,.46,.45,.94)",
			easeInOutQuad: "cubic-bezier(.455,.03,.515,.955)",
			easeInQuart: "cubic-bezier(.895,.03,.685,.22)",
			easeOutQuart: "cubic-bezier(.165,.84,.44,1)",
			easeInOutQuart: "cubic-bezier(.77,0,.175,1)",
			easeInQuint: "cubic-bezier(.755,.05,.855,.06)",
			easeOutQuint: "cubic-bezier(.23,1,.32,1)",
			easeInOutQuint: "cubic-bezier(.86,0,.07,1)",
			easeInSine: "cubic-bezier(.47,0,.745,.715)",
			easeOutSine: "cubic-bezier(.39,.575,.565,1)",
			easeInOutSine: "cubic-bezier(.445,.05,.55,.95)",
			easeInBack: "cubic-bezier(.6,-.28,.735,.045)",
			easeOutBack: "cubic-bezier(.175, .885,.32,1.275)",
			easeInOutBack: "cubic-bezier(.68,-.55,.265,1.55)"
		},
		monitor: function(obj)
		{
			if ((typeof obj) != "object") throw new Error("In the function 'Nature.Animation.execute', the type of the 'obj' param must be object.");
			this.newInstance(obj)._start();
		}
	}
});