/**
 * 气泡小贴士类
 * 
 * @width：宽度
 * @height：高度
 * @direction：方向（left, up, right, down）
 * @location：位置（left, up, right, down）
 * @closable：是否支持关闭（true，false）
 */
Nature.create({
	packages: 'pers.linhai.nature.util',
	className: 'BubbleTip',
	extend: "Nature.DOMElement",
	BubbleTip: function()
	{
		// 气泡容器div dom对象
		this.dom = Nature.createDom('div', "bubble_tip");

		// 箭头div对象
		this.arrow_div = Nature.createDom('div', "arrow");
		this.arrow_div.appendChild(Nature.createDom('em'));
		this.arrow_div.appendChild(Nature.createDom('label'));

		// 将箭头添加到气泡实体容器中
		this.dom.appendChild(this.arrow_div);

		this.elementToBeAimedAt = this.aimAt instanceof Nature.DOMElement ? this.aimAt : new Nature.DOMElement(this.aimAt);

		// 设置宽高
		this.setWidth(this.width);
		this.setHeight(this.height);

		// 渲染到Body标签作用域中
		this.render(Nature.getBody());

		// 添加气泡飞入动画
		Nature.Animation.monitor({
			property: "all",
			effect: 'easeInOutCirc',
			dom: this
		});

		var processor = new pers.linhai.nature.util.Processor({
			handler: this._setArrowAimAtPoint.bind(this),
			timeout: 500
		});

		Nature.eventUtil.addListener(window, 'resize', function(e)
		{
			processor.process();
		});

		if (this.direction == undefined)
		{
			Nature.eventUtil.addListener(window, 'scroll', function(e)
			{
				processor.process();
			});
		}

		// 设置箭头指向
		this._setArrowAimAtPoint();
	},
	_setArrowAimAtPoint: function()
	{
		// 定位的目标
		var direction = this.direction, location = this.location;
		if (direction == undefined)
		{
			var edgeDistance = this.elementToBeAimedAt.edgeDistance();
			direction = this._getDirection(edgeDistance);
			location = this._getLocation(direction, edgeDistance);
		}

		var d_l = this.constructor.DIRECTION_LOCATION[direction + "_" + location];
		d_l = d_l != undefined ? d_l : this.constructor.DIRECTION_LOCATION['down_left'];
		this.arrow_div.className = "arrow " + d_l.className;
		d_l.setPos(this.elementToBeAimedAt, this);
	},
	_getDirection: function(edgeDistance)
	{
		var w = this.getWidth(), h = this.getHeight();
		var edgeDistanceArr = [edgeDistance.left - w, edgeDistance.top - h, edgeDistance.right - w, edgeDistance.bottom - h], maxDistance = 0, directionIndex = undefined;

		// 优先选择方向为下和上
		if (edgeDistanceArr[1] > 30) directionIndex = 1;
		if (edgeDistanceArr[3] > 30 && edgeDistanceArr[3] > edgeDistanceArr[1]) directionIndex = 3;

		// 如果方向上和下都小于0，则选择最大值所属的方向
		if (directionIndex == undefined)
		{
			for (var i = 0; i < edgeDistanceArr.length; i++)
			{
				var tt = edgeDistanceArr[i];
				if (tt > maxDistance)
				{
					maxDistance = tt;
					directionIndex = i;
				}
			}
		}

		var d = "down";
		switch(directionIndex)
		{
			case 0:
				d = "right";
				break;
			case 1:
				d = "down";
				break;
			case 2:
				d = "left";
				break;
			case 3:
				d = "up";
				break;
			default:
				d = "down";
		}
		return d;
	},
	_getLocation: function(direction, edgeDistance)
	{
		if (direction == "left" || direction == "right")
		{
			return edgeDistance.top > edgeDistance.bottom ? "down" : "up";
		}
		else if (direction == "up" || direction == "down")
		{
			return edgeDistance.left > edgeDistance.right ? "right" : "left";
		}
	},
	statics: {
		DIRECTION_LOCATION: {
			left_down: {
				className: "left_down",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = x + w + 6;
					var top = (y + h / 2) - sh + 20;
					t.position(left, top);
				}
			},
			left_up: {
				className: "left_up",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = x + w + 6;
					var top = (y + h / 2) - 20;
					t.position(left, top);
				}
			},
			right_down: {
				className: "right_down",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = x - sw - 6;
					var top = (y + h / 2) - sh + 20;
					t.position(left, top);
				}
			},
			right_up: {
				className: "right_up",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = x - sw - 6;
					var top = (y + h / 2) - 20;
					t.position(left, top);
				}
			},
			up_left: {
				className: "up_left",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = (x + w / 2) - 20;
					var top = y + h + 6;
					t.position(left, top);
				}
			},
			up_right: {
				className: "up_right",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = (x + w / 2) - sw + 20;
					var top = y + h + 6;
					t.position(left, top);
				}
			},
			down_left: {
				className: "down_left",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = (x + w / 2) - 20;
					var top = y - sh - 6;
					t.position(left, top);
				}
			},
			down_right: {
				className: "down_right",
				setPos: function(e, t)
				{
					var ePos = e.position();
					var x = ePos.x, y = ePos.y, w = e.getWidth(), h = e.getHeight(), sw = t.getWidth(), sh = t.getHeight();
					var left = (x + w / 2) - sw + 20;
					var top = y - sh - 6;
					t.position(left, top);
				}
			}
		}
	}
});