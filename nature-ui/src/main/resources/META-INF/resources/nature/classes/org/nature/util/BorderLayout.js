/**
 * DIV布局类
 * 
 * @north:{ height: 100, divDom： dom }
 * @west:{ width: 100, divDom： dom }
 * @east:{ width: 100, divDom： dom }
 * @south:{ height: 100, divDom： dom }
 * @center: dom
 * @renderTo:""
 */
Nature.create({
	packages: 'org.nature.util',
	className: 'BorderLayout',
	BorderLayout: function(obj)
	{
		this.fragment = Nature.createFragment();
		this._build_north(obj.north);
		this._build_south(obj.south);
		this._build_west(obj.west);
		this._build_east(obj.east);
		this._build_center(obj.center);

		// 渲染
		this.render(obj.renderTo);
	},
	_build_north: function(_p)
	{
		if (_p)
		{
			this.north = new Nature.Element(_p.divDom ? _p.divDom : Nature.createDom("div"));
			this.north.addClass("border_layout_north");
			this.north.setHeight(_p.height);
			this.north.render(this.fragment);
		}
	},
	_build_south: function(_p)
	{
		if (_p)
		{
			this.south = new Nature.Element(_p.divDom ? _p.divDom : Nature.createDom("div"));
			this.south.addClass("border_layout_south");
			this.south.setHeight(_p.height);
			this.south.render(this.fragment);
		}
	},
	_build_west: function(_p)
	{
		if (_p)
		{
			this.west = new Nature.Element(_p.divDom ? _p.divDom : Nature.createDom("div"));
			this.west.addClass("border_layout_west");
			this.west.setWidth(_p.width);
			if (this.north) this.west.style("top", this.north.getHeight() + "px");
			if (this.south) this.west.style("bottom", this.south.getHeight() + "px");
			this.west.render(this.fragment);
		}
	},
	_build_east: function(_p)
	{
		if (_p)
		{
			this.east = new Nature.Element(_p.divDom ? _p.divDom : Nature.createDom("div"));
			this.east.addClass("border_layout_east");
			this.east.setWidth(_p.width);
			if (this.north) this.east.style("top", this.north.getHeight() + "px");
			if (this.south) this.east.style("bottom", this.south.getHeight() + "px");
			this.east.render(this.fragment);
		}
	},
	_build_center: function(_p)
	{
		this.center = new Nature.Element(_p ? _p : Nature.createDom("div"));
		this.center.addClass("border_layout_center");
		if (this.north) this.center.style("top", this.north.getHeight() + "px");
		if (this.south) this.center.style("bottom", this.south.getHeight() + "px");
		if (this.west) this.center.style("left", this.west.getWidth() + "px");
		if (this.east) this.center.style("right", this.east.getWidth() + "px");
		this.center.render(this.fragment);
	},
	render: function(_container)
	{
		var container = container instanceof Nature.Element ? container : new Nature.Element(Nature.getDom(_container));
		if (container) container.append(this.fragment);
	}
});