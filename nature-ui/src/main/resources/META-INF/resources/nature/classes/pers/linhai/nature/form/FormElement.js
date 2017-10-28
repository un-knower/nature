/**
 * 表单元素
 */
Nature.create({
	packages: 'pers.linhai.nature.form',
	className: 'FormElement',
	extend: "Nature.DOMElement",
	FormElement: function()
	{
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