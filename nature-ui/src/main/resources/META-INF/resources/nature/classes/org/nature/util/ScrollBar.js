/**
 * 自定义滚动条
 * 
 */
Nature.create({
	imports: 'org.nature.util.Processor',
	existsCss: true,
	packages: 'org.nature.util',
	className: 'ScrollBar',
	ScrollBar: function(_obj)
	{
		if (typeof _obj == 'object') this.extend(_obj);

		this._style = this.direction == 'horizontal' ? org.nature.util.ScrollBar._style.horizontal_style : org.nature.util.ScrollBar._style.vertical_style;
		this.container_div = Nature.createDom('div', this._style.scrollbar_container);
		this._init_both_ends();

		// IE下禁止标题元素被选取
		this.container_div.onselectstart = function()
		{
			return false;
		}

		if (this.hideArrow != true)
		{
			// 初始化前箭头
			this._init_front_arrow();

			// 初始化后箭头
			this._init_behind_arrow();
		}
		// 渲染
		this.render(this.renderTo);

		// 初始化滚动条
		this._init_scroll_bar();
	},
	_init_both_ends: function()
	{
		var front_end_div = Nature.createDom('div', this._style.front_end);
		this.container_div.appendChild(front_end_div);
		var behind_end_div = Nature.createDom('div', this._style.behind_end);
		this.container_div.appendChild(behind_end_div);
	},
	_init_front_arrow: function()
	{
		this.front_arrow_div = Nature.createDom('div', this._style.front_arrow_default);
		this.container_div.appendChild(this.front_arrow_div);
		Nature.eventUtil.addListener(this.front_arrow_div, 'mousedown', (function()
		{
			this.front_arrow_div.className = this._style.front_arrow_down;
			this.bar_move_direction = 'front';
			this._set_scroll_bar_location();
			var _mouseup_fn = (function()
			{
				this.front_arrow_div.className = this._style.front_arrow_default;
				if (this._set_scroll_bar_location_timeout != undefined) window.clearTimeout(this._set_scroll_bar_location_timeout);
				Nature.eventUtil.removeListener(Nature.doc, 'mouseup', _mouseup_fn);
			}).bind(this);
			Nature.eventUtil.addListener(Nature.doc, 'mouseup', _mouseup_fn);
		}).bind(this));
	},
	_init_behind_arrow: function()
	{
		this.behind_arrow_div = Nature.createDom('div', this._style.behind_arrow_default);
		this.container_div.appendChild(this.behind_arrow_div);
		Nature.eventUtil.addListener(this.behind_arrow_div, 'mousedown', (function()
		{
			this.behind_arrow_div.className = this._style.behind_arrow_down;
			this.bar_move_direction = 'behind';
			this._set_scroll_bar_location();
			var _mouseup_fn = (function()
			{
				this.behind_arrow_div.className = this._style.behind_arrow_default;
				if (this._set_scroll_bar_location_timeout != undefined) window.clearTimeout(this._set_scroll_bar_location_timeout);
				Nature.eventUtil.removeListener(Nature.doc, 'mouseup', _mouseup_fn);
			}).bind(this);
			Nature.eventUtil.addListener(Nature.doc, 'mouseup', _mouseup_fn);
		}).bind(this));
	},
	_set_scroll_bar_location: function()
	{
		var _diff = (this.bar_move_direction == 'behind' ? 1 : -1) * Math.ceil(Nature.ScrollBar._scroll_speed * this.scroll_bar_groove_size / this.rollingRange);
		var _v_min = 15;
		if (this.direction == 'horizontal')
		{
			var _v = parseInt(this.scroll_bar_div.style.left) + _diff;
			if (this.bar_move_direction == 'behind' && _v >= this.scroll_bar_max_move_location) _v = this.scroll_bar_max_move_location;
			else if (this.bar_move_direction == 'front' && _v <= _v_min) _v = _v_min;
			this.scroll_bar_div.style.left = _v + 'px';
		}
		else
		{
			var _v = parseInt(this.scroll_bar_div.style.top) + _diff;
			if (this.bar_move_direction == 'behind' && _v >= this.scroll_bar_max_move_location) _v = this.scroll_bar_max_move_location;
			else if (this.bar_move_direction == 'front' && _v <= _v_min) _v = _v_min;
			this.scroll_bar_div.style.top = _v + 'px';
		}

		this._do_call_back();
		this._set_scroll_bar_location_timeout = window.setTimeout(this._set_scroll_bar_location.bind(this), 200);
	},
	_do_call_back: function()
	{
		if (typeof this.callBack == 'function')
		{
			var cur_bar_location = parseInt(this.direction == 'horizontal' ? this.scroll_bar_div.style.left : this.scroll_bar_div.style.top);
			var cur_bar_location_change = cur_bar_location - (this.hideArrow != true ? 15 : 0);
			this.callBack(Math.ceil((cur_bar_location_change / this.scroll_bar_move_range) * this.scroll_content_move_range));
		}
	},
	_init_scroll_bar: function()
	{
		this.scroll_bar_div = Nature.createDom('div', this._style.scroll_bar_default);
		this.scroll_bar_div.setAttribute('name', 'scroll_bar_div');
		// IE下禁止标题元素被选取
		this.scroll_bar_div.onselectstart = function()
		{
			return false;
		}

		this.container_div.appendChild(this.scroll_bar_div);
		this.refresh();
		Nature.eventUtil.addListener(this.scroll_bar_div, 'mousedown', (function(e)
		{
			this.scroll_bar_div.className = this._style.scroll_bar_down;
			e = e || window.event;
			var _old_mouse_location = this.direction == 'horizontal' ? e.clientX : e.clientY, _location = parseInt(this.direction == 'horizontal' ? this.scroll_bar_div.style.left
					: this.scroll_bar_div.style.top);
			var _v_min = this.hideArrow != true ? 15 : 0;
			var _mousemove_fn = (function(e)
			{
				e = e || window.event;
				if (this.direction == 'horizontal')
				{
					var _diff = e.clientX - _old_mouse_location;
					var _v = _location + _diff;
					if (_diff > 0 && _v >= this.scroll_bar_max_move_location) _v = this.scroll_bar_max_move_location;
					else if (_diff < 0 && _v <= _v_min) _v = _v_min;
					this.scroll_bar_div.style.left = _v + 'px';
				}
				else
				{
					var _diff = e.clientY - _old_mouse_location;
					var _v = _location + _diff;
					if (_diff > 0 && _v >= this.scroll_bar_max_move_location) _v = this.scroll_bar_max_move_location;
					else if (_diff < 0 && _v <= _v_min) _v = _v_min;
					this.scroll_bar_div.style.top = _v + 'px';
				}
				this._do_call_back();
			}).bind(this);
			Nature.eventUtil.addListener(Nature.doc, 'mousemove', _mousemove_fn);

			var _mouseup_fn = (function()
			{
				this.scroll_bar_div.className = this._style.scroll_bar_default;
				Nature.eventUtil.removeListener(Nature.doc, 'mouseup', _mouseup_fn);
				Nature.eventUtil.removeListener(Nature.doc, 'mousemove', _mousemove_fn);
			}).bind(this);
			Nature.eventUtil.addListener(Nature.doc, 'mouseup', _mouseup_fn);
		}).bind(this));

		// 随着窗口大小改变，是否自动调整滚动条，以至能自适应浏览器，默认为false
		if (this.autoAdjust == true)
		{
			var processor = new org.nature.util.Processor({
				handler: this.refresh.bind(this)
			});
			Nature.eventUtil.addListener(window, 'resize', function()
			{
				processor.process();
			});
		}
	},
	refresh: function()
	{
		this.scroll_bar_groove_size = this.direction == 'horizontal' ? this.container_div.offsetWidth : this.container_div.offsetHeight;
		this.viewRange = this.direction == 'horizontal' ? this.viewDom.clientWidth : this.viewDom.clientHeight;
		this.rollingRange = this.direction == 'horizontal' ? this.viewDom.scrollWidth : this.viewDom.scrollHeight;
		var rollingLocation = this.direction == 'horizontal' ? this.viewDom.scrollLeft : this.viewDom.scrollTop;
		var scroll_bar_size = Math.floor((this.viewRange / this.rollingRange) * (this.scroll_bar_groove_size - (this.hideArrow != true ? 28 : 0)));
		scroll_bar_size = scroll_bar_size > 16 ? scroll_bar_size : 16;
		var bar_real_size = 0;
		if (this.direction == 'horizontal')
		{
			this.scroll_bar_div.style.width = scroll_bar_size + 'px';
			bar_real_size = this.scroll_bar_div.offsetWidth;
		}
		else
		{
			this.scroll_bar_div.style.height = scroll_bar_size + 'px';
			bar_real_size = this.scroll_bar_div.offsetHeight;
		}
		this.scroll_bar_max_move_location = this.scroll_bar_groove_size - bar_real_size - (this.hideArrow != true ? 15 : 0);
		this.scroll_bar_move_range = this.scroll_bar_max_move_location - (this.hideArrow != true ? 15 : 0);
		this.scroll_content_move_range = this.rollingRange - this.viewRange;
		var bar_location = Math.floor(rollingLocation / this.scroll_content_move_range * this.scroll_bar_move_range);
		bar_location = isNaN(bar_location) ? 0 : bar_location;
		if (this.direction == 'horizontal') this.scroll_bar_div.style.left = ((this.hideArrow != true ? 15 : 0) + bar_location) + 'px';
		else this.scroll_bar_div.style.top = ((this.hideArrow != true ? 15 : 0) + bar_location) + 'px';
	},
	render: function(_container)
	{
		var container = Nature.getDom(_container);
		if (container) container.appendChild(this.container_div);
	},
	statics: {
		_style: {
			horizontal_style: {
				scrollbar_container: 'horizontal_scrollbar_container',
				front_arrow_default: 'horizontal_front_arrow_default',
				front_arrow_down: 'horizontal_front_arrow_down',
				behind_arrow_default: 'horizontal_behind_arrow_default',
				behind_arrow_down: 'horizontal_behind_arrow_down',
				front_end: 'horizontal_front_end',
				behind_end: 'horizontal_behind_end',
				scroll_bar_default: 'horizontal_scroll_bar_default',
				scroll_bar_down: 'horizontal_scroll_bar_down'
			},
			vertical_style: {
				scrollbar_container: 'vertical_scrollbar_container',
				front_arrow_default: 'vertical_front_arrow_default',
				front_arrow_down: 'vertical_front_arrow_down',
				behind_arrow_default: 'vertical_behind_arrow_default',
				behind_arrow_down: 'vertical_behind_arrow_down',
				front_end: 'vertical_front_end',
				behind_end: 'vertical_behind_end',
				scroll_bar_default: 'vertical_scroll_bar_default',
				scroll_bar_down: 'vertical_scroll_bar_down'
			}
		},
		_scroll_speed: 20
	// 即每秒滚动内容区的20个像素单位
	}
});