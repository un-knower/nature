/**
 * 节流类throttle
 */
Nature.create({
	packages: 'org.nature.util',
	className: "Processor",
	timeout: 100,
	Processor: function(_obj)
	{
		if (typeof _obj == "object") this.extend(_obj);
		Nature.checkReference(this.handler, window.Function, "Nature.Processor@handler");
	},
	process: function(p)
	{
		if (this._timeoutid != undefined)
		{
			window.clearTimeout(this._timeoutid);
		}
		var _this = this;
		this._timeoutid = window.setTimeout(function()
		{
			_this.handler(p);
		}, this.timeout);
	}
});