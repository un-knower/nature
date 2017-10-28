/**
 * 通用GridTable组件
 */
Nature.create({
	packages: 'pers.linhai.nature.grid',
	imports: ['pers.linhai.nature.form.Text', 'pers.linhai.nature.util.BubbleTip', 'pers.linhai.nature.util.Processor', 'pers.linhai.nature.form.Select', 'pers.linhai.nature.form.IconButton'],
	className: 'GridTable',
	GridTable: function(obj)
	{
		this.container_div = Nature.createDom('div', 'table_container');

		// 1.初始化表格控件框架
		this._init_control_frame();

		// 2.初始化表头
		this._init_header(obj.fixedColumns, obj.columns);

		// 3.初始化表体
		this._init_body();

		// 4.渲染到页面
		this.render(obj.renderTo);

		// 5.初始化水平方向滚动条
		this._init_bottom_scroll_bar();

		if (obj.pagination === true)
		{
			this._init_page_bar();
		}

		// 6.初始化框架自适应调整监听器
		this._init_resize_listener();

		// 7.渲染table body中的数据
		this._set_data(obj.data);
	},
	_init_control_frame: function()
	{
		this.table_header_container_div = Nature.createDom('div', 'table_header_container');
		this.table_header_scroll_bar_div = Nature.createDom('div', 'table_header_scroll_bar');
		this.table_header_container_div.appendChild(this.table_header_scroll_bar_div);

		// IE下禁止标题元素被选取
		this.table_header_container_div.onselectstart = function()
		{
			return false;
		};

		// 用于添加fixedTable和Table对象的div，该div高度是自然高度，没有滚动条
		this.table_body_container_div = Nature.createDom('div', 'table_body_container');

		// 用于添加table_body_container_div的div对象，该div的高度为父容器的100%，滚动条overflow=auto
		this.table_body_container_parent_div = Nature.createDom('div', 'table_body_container_parent');
		this.table_body_container_parent_div.appendChild(this.table_body_container_div);

		// 分页工具条上部分DIV
		this.top_container_div = Nature.createDom('div', 'table_top_container');

		// 上部容器添加table_header_container_div和table_body_container_parent_div两个对象
		this.top_container_div.appendChild(this.table_header_container_div);
		this.top_container_div.appendChild(this.table_body_container_parent_div);

		// 最外层div对象添加top_container_div和bottom_container_div
		this.container_div.appendChild(this.top_container_div);

		// 分页工具条，及水平滚动条所属DIV
		this.bottom_container_div = Nature.createDom('div', 'table_bottom_container');

		// 水平滚动条DIV
		this.horizontal_scroll_bar_container_div = Nature.createDom('div', "horizontal_scroll_bar_container");
		this.bottom_container_div.appendChild(this.horizontal_scroll_bar_container_div);
		this.container_div.appendChild(this.bottom_container_div);
	},
	_init_page_bar: function()
	{
		this.pagination_bar_div = Nature.createDom('div', 'pagination_bar');
		var _pagination_table = this._create_table('pagination_table');
		this.pagination_bar_div.appendChild(_pagination_table);
		var _col_group_obj = Nature.createDom('colgroup');
		_col_group_obj.appendChild(this._create_col(150));
		_col_group_obj.appendChild(this._create_col(150));
		_pagination_table.appendChild(_col_group_obj);
		var pagination_bar_row = _pagination_table.insertRow(0);
		var total_record_cell = pagination_bar_row.insertCell(0);
		var total_record_label = Nature.createDom('label');
		this.total_record_value_span = Nature.createDom('span');
		this.total_record_value_span.innerHTML = '10000000';
		total_record_label.innerHTML = 'Total:';
		total_record_cell.appendChild(total_record_label);
		total_record_cell.appendChild(this.total_record_value_span);

		var pagination_size_cell = pagination_bar_row.insertCell(1);
		pagination_size_cell.style.textAlign = "right";
		pagination_size_cell.innerHTML = "<label>Page Size: </label>";
		new pers.linhai.nature.form.Select({
			width: 50,
			data: [[10, 10], [15, 15], [20, 20, true], [50, 50], [100, 100]],
			listeners: {
				"change": function()
				{
					alert(this.value);
				}
			},
			renderTo: pagination_size_cell
		});

		var pagination_cell = pagination_bar_row.insertCell(2);
		pagination_cell.className = "pagination_btn_td";

		// 添加刷新按钮
		new pers.linhai.nature.form.IconButton({
			iconUrl: Nature.baseClasspath.resourcesPath + "images/table/refresh.png",
			width: 40,
			height: 22,
			title: "Refresh current page",
			click: function()
			{

			},
			renderTo: pagination_cell
		});

		new pers.linhai.nature.form.IconButton({
			iconUrl: Nature.baseClasspath.resourcesPath + "images/table/page-first.png",
			width: 40,
			height: 22,
			click: function()
			{

			},
			renderTo: pagination_cell
		});

		new pers.linhai.nature.form.IconButton({
			iconUrl: Nature.baseClasspath.resourcesPath + "images/table/page-prev.png",
			width: 40,
			height: 22,
			click: function()
			{

			},
			renderTo: pagination_cell
		});

		// 添加页码输入框
		var pageNumberInput = new pers.linhai.nature.form.Text({
			helpTip: "",
			width: 80,
			height: 22,
			renderTo: pagination_cell
		});
		pageNumberInput.style("text-align", "center");

		new pers.linhai.nature.form.IconButton({
			iconUrl: Nature.baseClasspath.resourcesPath + "images/table/page-next.png",
			width: 40,
			height: 22,
			click: function()
			{

			},
			renderTo: pagination_cell
		});

		new pers.linhai.nature.form.IconButton({
			iconUrl: Nature.baseClasspath.resourcesPath + "images/table/page-last.png",
			width: 40,
			height: 22,
			click: function()
			{

			},
			renderTo: pagination_cell
		});
		window.setTimeout(function()
		{
			new pers.linhai.nature.util.BubbleTip({
				width: 300,
				height: 100,
				aimAt: pageNumberInput
			});
		}, 1000);

		this.bottom_container_div.appendChild(this.pagination_bar_div);
	},
	_init_bottom_scroll_bar: function()
	{
		this.horizontal_scroll_bar_div = Nature.createDom('div', "horizontal_scroll_bar");
		this.horizontal_scroll_bar_div.style.marginLeft = this.fixed_header_total_width + 'px';
		this.horizontal_scroll_bar_div.tbObj = this;
		this.horizontal_scroll_bar_div.onscroll = function()
		{
			this.tbObj.drawable_header_div.scrollLeft = this.tbObj.drawable_body_div.scrollLeft = this.scrollLeft;
		};

		var dd1 = Nature.createDom('div');
		dd1.style.position = "absolute";
		dd1.style.top = dd1.style.left = "0px";
		dd1.style.width = this.drawable_header_total_width + "px";
		dd1.style.height = "1px";
		this.horizontal_scroll_bar_div.appendChild(dd1);
		this.horizontal_scroll_bar_container_div.appendChild(this.horizontal_scroll_bar_div);
		var horizontal_scroll_bar_height = this.horizontal_scroll_bar_div.offsetHeight - this.horizontal_scroll_bar_div.clientHeight;
		this.horizontal_scroll_bar_container_div.style.height = (horizontal_scroll_bar_height > 15 ? horizontal_scroll_bar_height - 1 : horizontal_scroll_bar_height) + "px";
		this.horizontal_scroll_bar_div.style.top = horizontal_scroll_bar_height > 15 ? -(this.horizontal_scroll_bar_div.clientHeight + 1) + 'px' : -this.horizontal_scroll_bar_div.clientHeight + 'px';
	},
	_init_resize_listener: function()
	{
		var processor = new pers.linhai.nature.util.Processor({
			handler: this.reValidate.bind(this)
		});
		Nature.eventUtil.addListener(window, 'resize', function(e)
		{
			processor.process();
		});
		this.reValidate();
	},
	reValidate: function()
	{
		// 先执行这三句才能让表格正常自适应，并出滚动条
		this.top_container_div.style.height = this.table_body_container_parent_div.style.height = 'auto';

		// 当window窗口大小改变后，自动调整表格窗体各部件大小，以自适应表格所装载的容器；之所以先判断该对象的显隐藏，是因为下面需要计算bottom_container_div的高度，该高度的值需要包含该滚动条容器的高度
		this.horizontal_scroll_bar_container_div.style.display = this.drawable_header_div.scrollWidth > this.drawable_header_div.offsetWidth ? 'block' : 'none';

		// 刷新top_container_div的高度
		var top_container_div_height = this.container_div.offsetHeight - this.bottom_container_div.offsetHeight;
		this.top_container_div.style.height = top_container_div_height + 'px';

		// 刷新table_body_container_parent_div的高度
		var table_body_container_parent_div_height = top_container_div_height - this.table_header_container_div.offsetHeight;
		this.table_body_container_parent_div.style.height = table_body_container_parent_div_height + 'px';

		var verticalScrollBarWidth = this.table_body_container_div.offsetWidth - this.table_body_container_div.clientWidth;
		this.table_header_scroll_bar_div.style.width = this.horizontal_scroll_bar_div.style.marginRight = this.drawable_header_div.style.marginRight = verticalScrollBarWidth + "px";

		// 刷新滚动位置
		this.horizontal_scroll_bar_div.onscroll();
	},
	_init_header: function(fixedColumns, columns)
	{
		this.fixed_header_total_width = 0;
		this.drawable_header_total_width = 0;
		if (Array.isArray(fixedColumns) && fixedColumns.length > 0)
		{
			if (fixedColumns.length > 5) throw new Error("The length of fixed-head cant't exceed 5!");
			this.fixed_header_div = Nature.createDom('div', 'fixed_header');
			this._init_fixed_header(fixedColumns);
			this.fixed_header_div.appendChild(this.fixed_head_table);
			this.table_header_container_div.appendChild(this.fixed_header_div);
		}

		if (!Array.isArray(columns) || columns.length == 0)
		{
			throw new Error("The length of drawable-head must be at least one!");
		}

		this.drawable_header_div = Nature.createDom('div', 'drawable_header');
		this._init_drawable_header(columns);
		this.drawable_header_div.appendChild(this.drawable_head_table);
		this.table_header_container_div.appendChild(this.drawable_header_div);
	},
	_bind_column_bg: function(head_cell)
	{
		head_cell.onmouseover = head_cell.onmouseup = function()
		{
			this.className = "mouseover";
		}
		head_cell.onmousedown = function()
		{
			this.className = "mousedown";
		}
		head_cell.onmouseout = function()
		{
			this.className = "";
		}
	},
	_bind_column_attrs: function(head_cell, _column_info)
	{
		head_cell.fieldName = _column_info.fieldName;
		head_cell.fieldType = _column_info.fieldType;
		head_cell.cellAlign = _column_info.align;
		head_cell.sortable = _column_info.sortable === true;
		if (_column_info.defaultSortOrder) head_cell.defaultSortOrder = _column_info.defaultSortOrder.toLowerCase();
	},
	_bind_column_content: function(head_cell, columnName)
	{
		// set content
		var content_div = Nature.createDom("div", "header_content_container");
		content_div.innerHTML = columnName;
		head_cell.appendChild(content_div);
		head_cell.title = head_cell.textContent ? head_cell.textContent : head_cell.innerText;

		// set order info
		if (head_cell.sortable)
		{
			content_div.className += " header_content_container_marginright";
			var sort_div = Nature.createDom("div", "sort_container");
			sort_div.sortOrder = "";
			if ("asc,desc".contains(head_cell.defaultSortOrder))
			{
				sort_div.sortStatus = head_cell.defaultSortOrder;
				sort_div.className = "sort_container sort_" + sort_div.sortStatus;
			}
			head_cell.sort_div = sort_div;
			head_cell.appendChild(sort_div);
		}
	},
	_bind_column_click: function(head_cell)
	{
		head_cell.processor = new pers.linhai.nature.util.Processor({
			handler: this._sort_by_column,
			timeout: 300
		});
		head_cell.onclick = function()
		{
			if (this.isRunning == true) return;
			var sort_div = this.sort_div;
			switch(sort_div.sortStatus)
			{
				case "":
				{
					sort_div.className = "sort_container sort_asc";
					sort_div.sortStatus = "asc";
					break;
				}
				case "asc":
				{
					sort_div.className = "sort_container sort_desc";
					sort_div.sortStatus = "desc";
					break;
				}
				default:
				{
					sort_div.className = "sort_container";
					sort_div.sortStatus = "";
				}
			}
			this.processor.process(this);
		};
	},
	_sort_by_column: function(head_cell)
	{
		head_cell.isRunning = true;
		alert(head_cell.sort_div.sortStatus);
		head_cell.isRunning = false;
	},
	_init_fixed_header: function(fixedColumns)
	{
		this.fixed_head_table = this._create_table('header_table');

		// 将表格宽度设置为0 ，表格不会自适应
		this.fixed_head_table.style.width = '0px';
		var fixed_header_col_group_obj = Nature.createDom('colgroup');
		this.fixed_body_col_group_obj = Nature.createDom('colgroup');
		this.fixed_head_table.appendChild(fixed_header_col_group_obj);
		this.fixed_head_cells = [];
		var head_row_obj = this.fixed_head_table.insertRow(this.fixed_head_table.rows.length);
		for (var i = 0; i < fixedColumns.length; i++)
		{
			var _head = fixedColumns[i];
			var head_cell = head_row_obj.insertCell(i);
			this._bind_column_bg(head_cell);
			this._bind_column_attrs(head_cell, _head);
			this._bind_column_content(head_cell, _head.columnName);
			this._bind_column_click(head_cell);
			if (i == fixedColumns.length - 1) head_cell.style.borderRight = '1px solid #BFBFBF';
			if (typeof _head.formatter == 'function') head_cell.convert = _head.formatter;
			this.fixed_head_cells.push(head_cell);
			fixed_header_col_group_obj.appendChild(this._create_col(_head.width));
			this.fixed_body_col_group_obj.appendChild(this._create_col(_head.width));
			this.fixed_header_total_width += _head.width;
		}

		// 设置最外层容器的最小宽度为锁定列宽总和
		if (this.fixed_header_total_width > 700) this.container_div.style.minWidth = this.fixed_header_total_width + 'px';
	},
	_init_drawable_header: function(columns)
	{
		this.drawable_head_table = this._create_table('header_table');
		this.drawable_head_table.style.width = '100%';
		var drawable_head_col_group_obj = Nature.createDom('colgroup');
		this.drawable_body_col_group_obj = Nature.createDom('colgroup');
		this.drawable_head_table.appendChild(drawable_head_col_group_obj);
		this.drawable_head_cells = [];
		if (this.fixed_header_div) this.drawable_header_div.style.marginLeft = this.fixed_header_total_width + 'px';
		var head_row_obj = this.drawable_head_table.insertRow(this.drawable_head_table.rows.length);
		for (var i = 0; i < columns.length; i++)
		{
			var _head = columns[i];
			var head_cell = head_row_obj.insertCell(i);
			this._bind_column_bg(head_cell);
			this._bind_column_attrs(head_cell, _head);
			this._bind_column_content(head_cell, _head.columnName);
			this._bind_column_click(head_cell);
			if (typeof _head.formatter == 'function') head_cell.formatter = _head.formatter;
			this.drawable_head_cells.push(head_cell);

			drawable_head_col_group_obj.appendChild(this._create_col(_head.width));
			this.drawable_body_col_group_obj.appendChild(this._create_col(_head.width));
			this.drawable_header_total_width += _head.width;
			if (i == columns.length - 1) head_cell.style.borderRight = '0px';
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

		this.drawable_body_div = Nature.createDom('div', 'drawable_body');
		this._init_drawable_body();
		this.table_body_container_div.appendChild(this.drawable_body_div);
	},
	_init_fixed_body: function()
	{
		this.fixed_body_table = this._create_table('body_table');

		// 将表格宽度设置为0 ，表格不会自适应
		this.fixed_body_table.style.width = '0px';
		this.fixed_body_table.appendChild(this.fixed_body_col_group_obj);
	},
	_init_drawable_body: function()
	{
		this.drawable_body_table = this._create_table('body_table');
		this.drawable_body_table.style.width = '100%';
		this.drawable_body_table.appendChild(this.drawable_body_col_group_obj);
		if (this.fixed_body_div) this.drawable_body_div.style.marginLeft = this.fixed_header_total_width + 'px';
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
		if (Array.isArray(this.fixed_head_cells) && this.fixed_head_cells.length > 0)
		{
			if (this.fixed_body_table.parentNode == this.fixed_body_div) this.fixed_body_div.removeChild(this.fixed_body_table);
		}
		if (this.drawable_body_table.parentNode == this.drawable_body_div) this.drawable_body_div.removeChild(this.drawable_body_table);
		var _table_rows = this.drawable_body_table.rows;
		while(_table_rows.length > 0)
		{
			this.fixed_body_table.deleteRow(0);
			this.drawable_body_table.deleteRow(0);
		}
	},
	_set_data: function(data)
	{
		this._clear_data();
		if (data && data.length > 0)
		{
			var _mask_obj = new Nature.Mask({
				message: 'Please wait, while data is in loading...',
				renderTo: this.table_body_container_parent_div
			});

			var body_row_obj, _body_cell, _row_data, _head_cell, _data_index = 0, _data_load_num_each_cycle = 20;
			var _data_load_cycle = (function()
			{
				var _actual_data_load_num_each_cycle = 0;
				if (_data_index >= data.length)
				{
					if (Array.isArray(this.fixed_head_cells) && this.fixed_head_cells.length > 0)
					{
						this.fixed_body_div.appendChild(this.fixed_body_table);
					}
					this.drawable_body_div.appendChild(this.drawable_body_table);
					// 设置body横向滚动初始值为header当前横向滚动值
					this.drawable_body_div.scrollLeft = this.drawable_header_div.scrollLeft;
					_mask_obj.remove();
					_mask_obj = null;
					this.reValidate();
					return;
				}
				else if (_data_index + _data_load_num_each_cycle > data.length) _actual_data_load_num_each_cycle = data.length - _data_index;
				else if (_data_index + _data_load_num_each_cycle <= data.length) _actual_data_load_num_each_cycle = _data_load_num_each_cycle;

				for (var i = 0; i < _actual_data_load_num_each_cycle; i++)
				{
					_row_data = data[_data_index];
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
				window.setTimeout(_data_load_cycle, 10);
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
			_body_cell.innerHTML = 'No Data Found';
			_body_cell.title = _body_cell.textContent ? _body_cell.textContent : _body_cell.innerText;
		}
	},
	_set_cell_htmldata: function(_head_cell, _body_cell, _v)
	{
		var _cell_data = typeof _head_cell.convert == 'function' ? _head_cell.convert(_v) : _v;
		if (typeof _cell_data == 'string') _body_cell.innerHTML = '<label>' + _cell_data + '</label>';
		else if (typeof _cell_data == 'object') _body_cell.appendChild(_cell_data);
		_body_cell.title = _body_cell.textContent ? _body_cell.textContent : _body_cell.innerText;
		if (',left,center,right,'.contains(',' + _head_cell.cellAlign + ',')) _body_cell.style.textAlign = _head_cell.cellAlign;
	},
	_create_table: function(_class_name)
	{
		var _table = Nature.createDom('table', _class_name);
		_table.border = _table.cellPadding = _table.cellSpacing = 0;
		return _table;
	},
	_create_col: function(_width)
	{
		Nature.checkType(_width, "number", "pers.linhai.nature.grid.GridTable._create_col");
		var _col_obj = Nature.createDom('col');
		if (typeof _width == "number") _col_obj.width = _width;
		return _col_obj;
	},
	render: function(_container)
	{
		var container = Nature.getDom(_container);
		if (container)
		{
			container.style.overflow = "hidden";
			container.appendChild(this.container_div);
		}
	}
});