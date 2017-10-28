Nature.create({
	imports: 'org.nature.util.Animation',
	packages: 'org.nature.util',
	className: 'Mask',
	Mask: function(_obj){
		if(typeof _obj == 'object') this.extend(_obj);
		this.container_div = Nature.createDom('div', 'mask_container');
		this.container_div.appendChild(this._create_table());
		this.existOpacity = ("opacity" in this.container_div.style);
		//渲染
		this.render(this.renderTo);
	},
	_create_table: function(){
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
		return _table;
	},
	remove: function(){
		org.nature.util.Animation.execute({
			duration: typeof this.duration == 'number' ? this.duration : 500,
			change: 100,
			sine: 'sine.easeOut',
			play: (function(result){
				var s = this.container_div.style;
				if (this.existOpacity)s.opacity = (100 - result) / 100;
				else {
					s.zoom = 1;
					s.filter = "alpha(opacity=" + (100 - result) + ")";
				}
				s.width = s.height = "100%";
			}).bind(this),
			end: (function(){
				Nature.removeNode(this.container_div);
			}).bind(this)
		});
	},
	render: function(_container){
        var container = Nature.getDom(_container);
        if (container) container.appendChild(this.container_div);
	}
});