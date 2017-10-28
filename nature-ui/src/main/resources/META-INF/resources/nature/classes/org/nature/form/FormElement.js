/**
 * 表单元素
 */
Nature.create({
	packages: 'org.nature.form',
	className: 'FormElement',
	extend: "Nature.Element",
	FormElement: function(_obj)
	{
		if (typeof _obj == "object") this.extend(_obj);
	},
	setListeners: function(listeners)
	{
		if (typeof listeners == "object")
		{
			for (var k in listeners)
			{
				this.bind(k, listeners[k].bind(this.dom));
			}
		}
	}
});