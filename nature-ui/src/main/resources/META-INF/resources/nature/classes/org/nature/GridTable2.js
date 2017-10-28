/**
 * 通用GridTable组件
 */
Nature.create({
	packages: 'org.nature',
	imports: ['org.nature.util.ScrollBar', 'org.nature.util.Processor'],
	existsCss: true,
	className: 'GridTable',
	GridTable: function(_obj)
	{
		if (typeof _obj == 'object') this.extend(_obj);
		this.container_div = Nature.createDom('div', 'table_container');

		// 初始化表格控件框架
		this._init_control_frame();

		// 渲染到页面
		this.render(this.renderTo);

		// 初始化表头
		this._init_header();

		// 初始化表体
		this._init_body();

		// 初始化滚动条
		this._init_scroll_bar();

		if (this.hidePaginationBar != true)
		{
			this._init_page_bar();
		}

		// 初始化框架自适应调整监听器
		this._init_resize_listener();

		// 设置tablebody中的数据
		this._set_data();
	},
	_init_control_frame: function()
	{
		this.top_container_div = Nature.createDom('div', 'table_top_container');
		this.bottom_container_div = Nature.createDom('div', 'table_bottom_container');
		this.horizontal_scroll_bar_container_div = Nature.createDom('div', 'table_horizontal_scroll_bar_container');
		this.bottom_container_div.appendChild(this.horizontal_scroll_bar_container_div);
		this.table_header_container_div = Nature.createDom('div', 'table_header_container');
		// IE下禁止标题元素被选取
		this.bottom_container_div.onselectstart = this.table_header_container_div.onselectstart = function()
		{
			return false;
		}
		this.vertical_scroll_bar_container_top_div = Nature.createDom('div', 'vertical_scroll_bar_container_top');
		this.table_header_container_div.appendChild(this.vertical_scroll_bar_container_top_div);

		this.vertical_scroll_bar_container_div = Nature.createDom('div', 'vertical_scroll_bar_container');
		this.table_body_container_div = Nature.createDom('div', 'table_body_container');
		this.table_body_container_parent_div = Nature.createDom('div', 'table_body_container_parent');
		this.table_body_container_parent_div.appendChild(this.table_body_container_div);
		this.table_body_container_parent_div.appendChild(this.vertical_scroll_bar_container_div);
		this.top_container_div.appendChild(this.table_header_container_div);
		this.top_container_div.appendChild(this.table_body_container_parent_div);
		this.container_div.appendChild(this.top_container_div);
		this.container_div.appendChild(this.bottom_container_div);
	},
	_init_page_bar: function()
	{
		this.pagination_bar_div = Nature.createDom('div', 'pagination_bar');
		var _pagination_table = this._create_table('pagination_table');
		this.pagination_bar_div.appendChild(_pagination_table);
		var _col_group_obj = Nature.createDom('colgroup');
		_col_group_obj.appendChild(this._create_col(150));
		_col_group_obj.appendChild(this._create_col(30));
		_col_group_obj.appendChild(this._create_col(200));
		_pagination_table.appendChild(_col_group_obj);
		var pagination_bar_row = _pagination_table.insertRow(0);
		var total_record_cell = pagination_bar_row.insertCell(0);
		var total_record_label = Nature.createDom('label', 'total_record_label');
		this.total_record_value_label = Nature.createDom('label');
		this.total_record_value_label.innerHTML = '10000000';
		total_record_label.innerHTML = 'Total:';
		total_record_cell.appendChild(total_record_label);
		total_record_cell.appendChild(this.total_record_value_label);
		var refresh_cell = pagination_bar_row.insertCell(1);
		var refresh_btn_container_div = Nature.createDom('div', 'refresh_btn_container');
		this.refresh_btn_div = Nature.createDom('div', 'refresh_btn');
		refresh_btn_container_div.appendChild(this.refresh_btn_div);
		refresh_cell.appendChild(refresh_btn_container_div);
		var pagination_size_cell = pagination_bar_row.insertCell(2);
		var refresh_cell = pagination_bar_row.insertCell(3);
		this.bottom_container_div.appendChild(this.pagination_bar_div);
	},
	_init_scroll_bar: function()
	{
		// 初始化水平方向滚动条
		this.horizontal_scroll_bar = new org.nature.util.ScrollBar({
			direction: 'horizontal',
			viewDom: this.drawable_header_div,
			hideArrow: true,
			autoAdjust: false,
			callBack: (function(_scroll_left)
			{
				this.drawable_header_div.scrollLeft = this.drawable_body_div.scrollLeft = _scroll_left;
			}).bind(this),
			renderTo: this.horizontal_scroll_bar_container_div
		});

		this.vertical_scroll_bar = new org.nature.util.ScrollBar({
			direction: 'vertical',
			viewDom: this.table_body_container_div,
			hideArrow: true,
			autoAdjust: false,
			callBack: function(_scroll_top)
			{
				this.viewDom.scrollTop = _scroll_top;
			},
			renderTo: this.vertical_scroll_bar_container_div
		});
	},
	_init_resize_listener: function()
	{
		var processor = new org.nature.util.Processor({
			handler: this._window_resize_listener.bind(this)
		});
		Nature.eventUtil.addListener(window, 'resize', function(e)
		{
			processor.process();
		});
		this._window_resize_listener();
	},
	_window_resize_listener: function()
	{
		// 先执行这三句才能让表格正常自适应，并出滚动条
		if (this.hiddenPaginationBar == true)
		{
			this.table_body_container_parent_div.style.height = 'auto';
			this.top_container_div.style.height = 'auto';
		}

		// 控制水平方向滚动条显示与隐藏
		this._control_horizontal_scroll_bar_viewhidden();
		this._control_vertical_scroll_bar_viewhidden();
	},
	_control_vertical_scroll_bar_viewhidden: function()
	{
		if (this.table_body_container_div != undefined && this.table_body_container_div.scrollHeight - this.table_body_container_div.offsetHeight > 1)
		{
			this.vertical_scroll_bar_container_div.style.display = this.vertical_scroll_bar_container_top_div.style.display = 'block';
			// 刷新垂直滚动条状态
			if (this.vertical_scroll_bar) this.vertical_scroll_bar.refresh();
		}
		else this.vertical_scroll_bar_container_div.style.display = this.vertical_scroll_bar_container_top_div.style.display = 'none';
		this.drawable_header_div.style.marginRight = this.table_body_container_div.style.marginRight = this.vertical_scroll_bar_container_div.offsetWidth + 'px';
		if (this.horizontal_scroll_bar_container_div.style.display == 'block')
		{
			// 刷新横向滚动条状态
			if (this.horizontal_scroll_bar) this.horizontal_scroll_bar.refresh();
		}
	},
	_control_horizontal_scroll_bar_viewhidden: function()
	{
		if (this.drawable_header_div != undefined && this.drawable_header_div.scrollWidth - this.drawable_header_div.offsetWidth > 1) this.horizontal_scroll_bar_container_div.style.display = 'block';
		else this.horizontal_scroll_bar_container_div.style.display = 'none';
		var top_container_div_height, table_body_container_parent_div_height;
		top_container_div_height = this.container_div.clientHeight - this.bottom_container_div.offsetHeight;
		top_container_div_height = top_container_div_height > 62 ? top_container_div_height : 62;
		table_body_container_parent_div_height = top_container_div_height - this.table_header_container_div.offsetHeight;
		this.top_container_div.style.height = top_container_div_height + 'px';
		this.table_body_container_parent_div.style.height = table_body_container_parent_div_height + 'px';
	},
	_init_header: function()
	{
		if (Array.isArray(this.fixedColumns) && this.fixedColumns.length > 0)
		{
			if (this.fixedColumns.length > 5) throw new Error("The length of fixed-head cant't exceed 5!");
			this.fixed_header_div = Nature.createDom('div', 'fixed_header');
			this._init_fixed_header();
			this.fixed_header_div.appendChild(this.fixed_head_table);
			this.table_header_container_div.appendChild(this.fixed_header_div);
			delete this.fixedColumns;
		}
		if (Array.isArray(this.columns) && this.columns.length > 0)
		{
			this.drawable_header_div = Nature.createDom('div', 'drawable_header');
			this._init_drawable_header();
			this.drawable_header_div.appendChild(this.drawable_head_table);
			this.table_header_container_div.appendChild(this.drawable_header_div);
			delete this.columns;
		}
		else throw new Error("The length of drawable-head must be at least one!");
	},
	_init_fixed_header: function()
	{
		this.fixed_head_table = this._create_table('header_table');
		this.fixed_head_table.style.width = '0px';
		var fixed_header_col_group_obj = Nature.createDom('colgroup');
		this.fixed_body_col_group_obj = Nature.createDom('colgroup');
		this.fixed_head_table.appendChild(fixed_header_col_group_obj);
		this.fixed_head_cells = [];
		var head_row_obj = this.fixed_head_table.insertRow(this.fixed_head_table.rows.length);
		for (var i = 0; i < this.fixedColumns.length; i++)
		{
			var _head = this.fixedColumns[i];
			var head_cell = head_row_obj.insertCell(i);
			head_cell.onmousedown = function()
			{
			}
			head_cell.onmouseup = function()
			{
			}
			head_cell.innerHTML = _head.columnName;
			head_cell.fieldName = _head.fieldName;
			head_cell.fieldType = _head.fieldType;
			head_cell.cellAlign = _head.align;
			if (i == this.fixedColumns.length - 1) head_cell.style.borderRight = '1px solid #BFBFBF';
			if (typeof _head.converter == 'function') head_cell.convert = _head.converter;
			this.fixed_head_cells.push(head_cell);
			fixed_header_col_group_obj.appendChild(this._create_col(_head.width));
			this.fixed_body_col_group_obj.appendChild(this._create_col(_head.width));
		}

		// 设置最外层容器的最小宽度为锁定列宽总和
		this.container_div.style.minWidth = (this.fixed_header_div.offsetWidth + 100) + 'px';
		this.container_div.style.minHeight = 137 + 'px';
	},
	_init_drawable_header: function()
	{
		this.drawable_head_table = this._create_table('header_table');
		this.drawable_head_table.style.width = '100%';
		var drawable_head_col_group_obj = Nature.createDom('colgroup');
		this.drawable_body_col_group_obj = Nature.createDom('colgroup');
		this.drawable_head_table.appendChild(drawable_head_col_group_obj);
		this.drawable_head_cells = [];
		if (this.fixed_header_div) this.drawable_header_div.style.marginLeft = (this.fixed_header_div.offsetWidth - 1) + 'px';
		var head_row_obj = this.drawable_head_table.insertRow(this.drawable_head_table.rows.length);
		for (var i = 0; i < this.columns.length; i++)
		{
			var _head = this.columns[i];
			var head_cell = head_row_obj.insertCell(i);
			head_cell.innerHTML = _head.columnName;
			head_cell.fieldName = _head.fieldName;
			head_cell.fieldType = _head.fieldType;
			head_cell.cellAlign = _head.align;
			if (typeof _head.converter == 'function') head_cell.converter = _head.converter;
			this.drawable_head_cells.push(head_cell);

			drawable_head_col_group_obj.appendChild(this._create_col(_head.width));
			this.drawable_body_col_group_obj.appendChild(this._create_col(_head.width));
			if (i == this.columns.length - 1) head_cell.style.borderRight = '0px';
		}
	},
	_init_body: function()
	{
		if (Array.isArray(this.fixed_head_cells) && this.fixed_head_cells.length > 0)
		{
			this.fixed_body_div = Nature.createDom('div', 'fixed_body');
			this._init_fixed_body();
			this.table_body_container_div.appendChild(this.fixed_body_div);
		}
		if (Array.isArray(this.drawable_head_cells) && this.drawable_head_cells.length > 0)
		{
			this.drawable_body_div = Nature.createDom('div', 'drawable_body');
			this._init_drawable_body();
			this.table_body_container_div.appendChild(this.drawable_body_div);
		}
	},
	_init_fixed_body: function()
	{
		this.fixed_body_table = this._create_table('body_table');
		this.fixed_body_table.style.width = '0px';
		this.fixed_body_table.appendChild(this.fixed_body_col_group_obj);
	},
	_init_drawable_body: function()
	{
		this.drawable_body_table = this._create_table('body_table');
		this.drawable_body_table.style.width = '100%';
		this.drawable_body_table.appendChild(this.drawable_body_col_group_obj);
		if (this.fixed_body_div) this.drawable_body_div.style.marginLeft = (this.fixed_header_div.offsetWidth - 1) + 'px';
	},
	_bind_row_bg: function(_row_obj)
	{
		_row_obj.className = _row_obj.rowIndex % 2 == 0 ? 'row_odd_bg' : 'row_double_bg';

		var _this = this;
		_row_obj.onmouseover = function(e)
		{
			if (this._is_fixed_table) this.className = _this.drawable_body_table.rows[this.rowIndex].className = 'row_bg_hover';
			else this.className = (_this.fixed_head_table ? (_this.fixed_body_table.rows[this.rowIndex].className = 'row_bg_hover') : 'row_bg_hover');
		}
		_row_obj.onmouseout = function(e)
		{
			var _class_name_value = this.rowIndex % 2 == 0 ? 'row_odd_bg' : 'row_double_bg';
			if (this._is_fixed_table) this.className = _this.drawable_body_table.rows[this.rowIndex].className = _class_name_value;
			else this.className = (_this.fixed_head_table ? (_this.fixed_body_table.rows[this.rowIndex].className = _class_name_value) : _class_name_value);
		}
	},
	_clear_data: function()
	{
		if (this.fixed_body_table.parentNode == this.fixed_body_div) this.fixed_body_div.removeChild(this.fixed_body_table);
		if (this.drawable_body_table.parentNode == this.drawable_body_div) this.drawable_body_div.removeChild(this.drawable_body_table);
		var _table_rows = this.drawable_body_table.rows;
		while(_table_rows.length > 0)
		{
			this.fixed_body_table.deleteRow(0);
			this.drawable_body_table.deleteRow(0);
		}
	},
	_set_data: function()
	{
		this._clear_data();
		this._window_resize_listener();
		if (this.data && this.data.length > 0)
		{
			var _mask_obj = new Nature.Mask({
				message: 'Please wait, while data is in loading...',
				renderTo: this.table_body_container_parent_div
			});

			var body_row_obj, _body_cell, _row_data, _head_cell, _data_index = 0, _data_load_num_each_cycle = 20;
			var _data_load_cycle = (function()
			{
				var _actual_data_load_num_each_cycle = 0;
				if (_data_index >= this.data.length)
				{
					this.fixed_body_div.appendChild(this.fixed_body_table);
					this.drawable_body_div.appendChild(this.drawable_body_table);
					// 设置body横向滚动初始值为header当前横向滚动值
					this.drawable_body_div.scrollLeft = this.drawable_header_div.scrollLeft;
					_mask_obj.remove();
					_mask_obj = null;
					this._window_resize_listener();
					// window.setTimeout(this._set_data.bind(this), 1500);
					return;
				}
				else if (_data_index + _data_load_num_each_cycle > this.data.length) _actual_data_load_num_each_cycle = this.data.length - _data_index;
				else if (_data_index + _data_load_num_each_cycle <= this.data.length) _actual_data_load_num_each_cycle = _data_load_num_each_cycle;

				for (var i = 0; i < _actual_data_load_num_each_cycle; i++)
				{
					_row_data = this.data[_data_index];
					if (this.fixed_head_table)
					{
						body_row_obj = this.fixed_body_table.insertRow(this.fixed_body_table.rows.length);
						body_row_obj._is_fixed_table = true;
						this._bind_row_bg(body_row_obj);
						for (var j = 0; j < this.fixed_head_cells.length; j++)
						{
							_head_cell = this.fixed_head_cells[j];
							_body_cell = body_row_obj.insertCell(j);
							this._set_cell_htmldata(_head_cell, _body_cell, _row_data[_head_cell.fieldName]);
							if (j == this.fixed_head_cells.length - 1) _body_cell.style.borderRight = '1px solid #DFDFDF';
						}
					}
					body_row_obj = this.drawable_body_table.insertRow(this.drawable_body_table.rows.length);
					body_row_obj._is_fixed_table = false;
					this._bind_row_bg(body_row_obj);
					for (var j = 0; j < this.drawable_head_cells.length; j++)
					{
						_head_cell = this.drawable_head_cells[j];
						_body_cell = body_row_obj.insertCell(j);
						this._set_cell_htmldata(_head_cell, _body_cell, _row_data[_head_cell.fieldName]);
						if (j == this.drawable_head_cells.length - 1) _body_cell.style.borderRight = '0px';
					}
					_data_index++;
				}
				window.setTimeout(_data_load_cycle, 100);
			}).bind(this);

			_data_load_cycle.call(this);
		}
		else
		{
			this._set_no_record(this.fixed_body_table, this.fixed_head_cells, true);
			this._set_no_record(this.drawable_body_table, this.drawable_head_cells, false);

			this.fixed_body_div.appendChild(this.fixed_body_table);
			this.drawable_body_div.appendChild(this.drawable_body_table);
		}
	},
	_set_no_record: function(_table, _head_cells, _is_fixed_table)
	{
		if (_table && _head_cells)
		{
			var body_row_obj = _table.insertRow(_table.rows.length);
			body_row_obj._is_fixed_table = _is_fixed_table;
			this._bind_row_bg(body_row_obj);
			var _body_cell = body_row_obj.insertCell(0);
			_body_cell.colSpan = _head_cells.length;
			_body_cell.style.color = 'gray';
			_body_cell.style.fontStyle = 'italic';
			_body_cell.style.textAlign = 'center';
			_body_cell.innerHTML = 'No Record';
		}
	},
	_set_cell_htmldata: function(_head_cell, _body_cell, _v)
	{
		var _cell_data = typeof _head_cell.convert == 'function' ? _head_cell.convert(_v) : _v;
		if (typeof _cell_data == 'string') _body_cell.innerHTML = '<label>' + _cell_data + '</label>';
		else if (typeof _cell_data == 'object') _body_cell.appendChild(_cell_data);
		if (',left,center,right,'.contains(',' + _head_cell.cellAlign + ',')) _body_cell.style.textAlign = _head_cell.cellAlign;
	},
	_create_table: function(_class_name)
	{
		var _table = Nature.createDom('table', _class_name);
		_table.border = 0;
		_table.cellPadding = 0;
		_table.cellSpacing = 0;
		return _table;
	},
	_create_col: function(_width)
	{
		var _col_obj = Nature.createDom('col');
		_col_obj.width = _width;
		return _col_obj;
	},
	render: function(_container)
	{
		var container = Nature.getDom(_container);
		if (container) container.appendChild(this.container_div);
	}
});