/**
 * class: org.nature.form.Select
 * 
 * @params:
 * @width: number or string(like 100px)
 * @height: number or string(like 100px)
 * @data: [[1, "text1"],[1, "text2", true(选中)]]
 * @url: http://...
 */
Nature.create({
	packages: 'org.nature.form',
	className: 'Select',
	extend: 'org.nature.form.FormElement',
	Select: function()
	{
		this.dom = Nature.createDom('select', "common_input");

		// 设置宽高
		this.setWidth(this.width);
		this.setHeight(this.height);
		this.setListeners(this.listeners);
		if (typeof this.url == 'string')
		{
			var _this = this;
			Nature.Ajax.request({
				url: this.url,
				method: "GET",
				async: true,
				success: function(_xhr)
				{
					try
					{
						_this.setData(new Function("return " + _xhr.responseText + ";"));
					}
					catch(e)
					{
						throw e;
					}
				},
				failure: function(_error_info)
				{
					throw new Error("org.nature.form.Select 数据加载出错:" + _error_info + "\n url: " + this.url);
				}
			});
		}
		else
		{
			// 设置下拉数据源
			this.setData(this.data);
		}

		// 渲染到指定容器
		this.render(this.renderTo);
	},
	setData: function(data)
	{
		if (Array.isArray(data))
		{
			var item = null;
			for (var i = 0; i < data.length; i++)
			{
				item = data[i];
				this.dom.options.add(new Option(item[0], item[1]));
				if (item[2] === true)
				{
					this.dom.value = item[0];
				}
			}
		}
	},
	removeAll: function()
	{
		this.dom.options.length = 0;
	},
	remove: function(index)
	{
		this.dom.options.remove(index);
	},
	getSelectedItem: function()
	{
		return this.dom.options[this.dom.selectedIndex];
	},
	getValue: function()
	{
		return this.getSelectedItem().value;
	},
	getText: function()
	{
		return this.getSelectedItem().text;
	}
});