/**
 * dom元素扩展
 */
Nature.create({
	packages: 'org.nature',
	className: "Element",
	Element: function(dom){
		this.dom = Nature.getDom(dom);
		if (!this.dom || this.dom.nodeType !== 1) throw new Error("Create Element failed, the dom obj is undefined or the dom nodeType is not a Element type");
		this.listeners = {};
	},
	attr: function(){
		if(arguments.length == 0)throw new Error("The length of the arguments in the method 'Nature.Element.attr' must be one or two.");
		var fa = arguments[0];
		if(typeof fa == "string"){
			if(arguments.length == 1) return this.dom.getAttribute(fa);
			else if (arguments.length == 2) {
				this.dom.setAttribute(fa, arguments[1]);
				return this;
			}
		}else if(typeof fa == "object" && arguments.length == 1){
			for(var k in fa){
				if(fa.hasOwnProperty(k)) this.attr(k, fa[k]);
			}
			return this;
		}else throw new Error("The length of the arguments in the method 'Nature.Element.attr' must be one or two.");
	},
	prop: function(){
		if(arguments.length == 0)throw new Error("The length of the arguments in the method 'Nature.Element.prop' must be one or two.");
		var fa = arguments[0];
		if(typeof fa == "string"){
			if(arguments.length == 1) return this.dom[fa];
			else if (arguments.length == 2) {
				this.dom[fa] = arguments[1];
				return this;
			}
		}else if(typeof fa == "object" && arguments.length == 1){
			for(var k in fa){
				if(fa.hasOwnProperty(k)) this.prop(k, fa[k]);
			}
			return this;
		}else throw new Error("The length of the arguments in the method 'Nature.Element.prop' must be one or two.");
	},
	hasAttr: function(attr){
		return (attr in this.dom.attributes);
	},
	hasProp: function(prop){
		return (prop in this.dom);
	},
	removeAttr: function(attr){
		this.dom.removeAttribute(attr);
		return this;
	},
	removeProp: function(prop){
		delete this.dom[prop];
		return this;
	},
    hide: function(){
        return this.style("display", "none");
    },
    show: function(){
        return this.style("display", "block");
    },
	html: function(){
		if (arguments.length == 1) return this.prop("innerHTML", arguments[0]);
		else if (arguments.length == 0) return this.dom.innerHTML;
	},
	value: function(){
		if (arguments.length == 1) return this.prop("value", arguments[0]);
		else if (arguments.length == 0) return this.dom.value;
	},
	width: function(){
		if(arguments.length == 0) return this.dom.offsetWidth;
		else if(arguments.length == 1){
			var w = arguments[0];
			Nature.checkType(w, "number", "Nature.Element.width");
			return this.style("width", w + "px");
		}
	},
	height: function(){
		if(arguments.length == 0) return this.dom.offsetHeight;
		else if(arguments.length == 1){
			var h = arguments[0];
			Nature.checkType(h, "number", "Nature.Element.height");
			return this.style("height", h + "px");
		}
	},
	enable: function(){
		return this.prop("disabled", false);
	},
	disable: function(){
		return this.prop("disabled", true);
	},
    focus: function(){
        try {
            this.dom.focus();
        }catch (e) {}
        return this;
    },
    blur: function(){
        try {
            this.dom.blur();
        }catch (e) {}
        return this;
    },
    addClass: function(className){
		if(typeof className != "string") return this;
        if (className && !this.hasClass(className)) this.dom.className = this.dom.className + " " + className;
        return this;
    },
    removeClass: function(className){
        if (!className || !this.dom.className) return this;
        if (this.hasClass(className)) {
			var re = new RegExp("(?:^|\\s+)" + className + "(?:\\s+|$)", "g");
            this.dom.className = this.dom.className.replace(re, " ");
        }
        return this;
    },
    hasClass: function(className){
        return className && (" " + this.dom.className + " ").contains(" " + className + " ");
    },
    replaceClass: function(oldClassName, newClassName){
        return this.removeClass(oldClassName).addClass(newClassName);
    },
    style: function(){
		if(arguments.length == 0)throw new Error("The method 'Nature.Element.style' must have at least one param.");
		var prop = arguments[0];
		if(typeof prop == "string"){
			prop = Nature.cssProp(prop).name;
			if (arguments.length == 1) {
				var _v, cs, el = this.dom;
				if(_v = el.style[prop]) return _v;
				if(window.getComputedStyle) return window.getComputedStyle(el, null)[prop];
				if (cs = el.currentStyle) return cs[prop];
				return null;
			}else if (arguments.length == 2) {
				var v = arguments[1];
				Nature.checkType(v, "string", "Nature.Element.style@2");
				if(prop == "opacity") return this.setOpacity(v);
				this.dom.style[prop] = v;
				return this;
			}
		}else if(typeof prop == "object" && arguments.length == 1){
			for(var k in prop){
				if(prop.hasOwnProperty(k)) this.style(k, prop[k]);
			}
			return this;
		}else throw new Error("The method 'Nature.Element.style' received an error type param.");
    },
    setOpacity: function(opacity){
		var s = this.dom.style;
		if (Nature.cssProp("opacity").supports)s.opacity = opacity / 100;
		else {
			s.zoom = 1;
			s.filter = "alpha(opacity=" + opacity + ")";
		}
        return this;
    },
	position: function(l, t, r, b){
		if(arguments.length > 0){
			if(typeof l == "number") this.style("left", l+"px");
			if(typeof t == "number") this.style("top", t+"px");
			if(typeof r == "number") this.style("right", r+"px");
			if(typeof b == "number") this.style("bottom", b+"px");
			return this;
		}
		var domEl = this.dom, B = Nature.getBody();
		if(domEl == B) return {x:0, y:0};
		if(domEl.getBoundingClientRect){
			var M = domEl.getBoundingClientRect(), N = Nature.getDocumentScroll();
			return {x:(M.left + N.left), y:(M.top + N.top)};
		}
		var X = 0, Y = 0, F = domEl;
		while(F){
            X += F.offsetLeft;
            Y += F.offsetTop;
			F = F.offsetParent;
		}
		return {x:X, y:Y};
    },
	_get_listener_list: function(en){
		var list = this.listeners[en];
		if(!Array.isArray(list)){
			list = [];
			this.listeners[en] = list;
		}
		return list;
	},
	_remove_cache_listener: function(en, f){
		this._get_listener_list(en).remove(f);
	},
	_cache_listener: function(en, f){
		this._remove_cache_listener();
		this._get_listener_list(en).push(f);
	},
	bind: function(en, f){
		en = Nature.eventUtil.formatEventName(en);
		this._cache_listener(en, f);
		Nature.eventUtil.addListener(this.dom, en, f);
		return this;
	},
	unBind: function(en, f){
		en = Nature.eventUtil.formatEventName(en);
		this._remove_cache_listener(en, f);
		Nature.eventUtil.removeListener(this.dom, en, f);
		return this;
	},
	unBindAll: function(){
		var listeners = this.listeners, list = null;
		for(var en in listeners){
			if(listeners.hasOwnProperty(en)){
				list = listeners[en];
				for(var i = 0 ;i < list.length; i++) this.unBind(en, list[i]);
			}
		}
		return this;
	},
	render: function(container){
		var container_dom = (container instanceof Nature.Element) ? container.dom : Nature.getDom(container);
		if(container_dom) container_dom.appendChild(this.dom);
		return this;
	}
});