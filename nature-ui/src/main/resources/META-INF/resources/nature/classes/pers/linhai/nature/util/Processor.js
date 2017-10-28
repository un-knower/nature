/**
 * 节流类throttle
 */
Nature.create({
	packages: 'pers.linhai.nature.util',
	className: "Processor",
	timeout: 100,
	Processor: function()
	{
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