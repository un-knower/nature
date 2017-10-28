/**
 * 动画类
 */
Nature.create({
	packages: 'org.nature.util',
	className: 'Animation',
	duration: 500,
	begin: 0,
	effect: 'sine.easeOut',
	_request_animation_frame: (function(){
		return this.requestAnimationFrame || this.mozRequestAnimationFrame || this.webkitRequestAnimationFrame || this.msRequestAnimationFrame || 
		function(f){
			window.setTimeout(f, 1);
		};
	}).call(window),
	Animation: function(_obj){
		if(typeof _obj == 'object') this.extend(_obj);
		if(typeof this.play != 'function') throw new Error("In the function 'Nature.Animation', the type of the 'play' param must be function.");
		if(typeof this.change != 'number') throw new Error("In the function 'Nature.Animation', the type of the 'change' param must be number.");
	},
	_start: function(){
		this._doEffect = new Function('return this.effects.'+this.effect + ';').call(this.constructor);
		this._animate = this._animate.bind(this);
		this._startTime = new Date().getTime();
		this._animate();
	},
	_animate: function(){
		this.time = new Date().getTime() - this._startTime;
		this.time = this.time > this.duration ? this.duration : this.time;
		if (this.time < this.duration) {
			this._request_animation_frame.call(window, this._animate);
			this.play(Math.ceil(this._doEffect(this.time, this.begin, this.change, this.duration)));
		}
		else this._stop();
	},
	_stop: function(){
		if(typeof this.end == 'function'){
			this.end();
		}
	},
    _static: {
		effects: {
			/**
			 * 动画效果:
			 * t: current time（当前时间）；
			 * b: beginning value（初始值）；
			 * c: change in value（变化量）；
			 * d: duration（持续时间）。
			 * ps：Elastic和Back有其他可选参数，里面都有说明。
			 * 
			 * 每个效果都分三个缓动方式（方法），分别是：
			 * easeIn：从0开始加速的缓动；
			 * easeOut：减速到0的缓动；
			 * easeInOut：前半段从0开始加速，后半段减速到0的缓动。
			 * 其中Linear是无缓动效果，没有以上效果。
			*/
			linear:function (t, b, c, d) {
		        return c * t / d + b;
		    },
		    quad:{
		        easeIn:function (t, b, c, d) {
		            return c * (t /= d) * t + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return -c * (t /= d) * (t - 2) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if ((t /= d / 2) < 1) return c / 2 * t * t + b;
		            return -c / 2 * ((--t) * (t - 2) - 1) + b;
		        }
		    },
		    cubic:{
		        easeIn:function (t, b, c, d) {
		            return c * (t /= d) * t * t + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return c * ((t = t / d - 1) * t * t + 1) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if ((t /= d / 2) < 1) return c / 2 * t * t * t + b;
		            return c / 2 * ((t -= 2) * t * t + 2) + b;
		        }
		    },
		    quart:{
		        easeIn:function (t, b, c, d) {
		            return c * (t /= d) * t * t * t + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return -c * ((t = t / d - 1) * t * t * t - 1) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if ((t /= d / 2) < 1) return c / 2 * t * t * t * t + b;
		            return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
		        }
		    },
		    quint:{
		        easeIn:function (t, b, c, d) {
		            return c * (t /= d) * t * t * t * t + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if ((t /= d / 2) < 1) return c / 2 * t * t * t * t * t + b;
		            return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
		        }
		    },
		    sine:{
		        easeIn:function (t, b, c, d) {
		            return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return c * Math.sin(t / d * (Math.PI / 2)) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
		        }
		    },
		    expo:{
		        easeIn:function (t, b, c, d) {
		            return (t == 0) ? b : c * Math.pow(2, 10 * (t / d - 1)) + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return (t == d) ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if (t == 0) return b;
		            if (t == d) return b + c;
		            if ((t /= d / 2) < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
		            return c / 2 * (-Math.pow(2, -10 * --t) + 2) + b;
		        }
		    },
		    circ:{
		        easeIn:function (t, b, c, d) {
		            return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
		        },
		        easeOut:function (t, b, c, d) {
		            return c * Math.sqrt(1 - (t = t / d - 1) * t) + b;
		        },
		        easeInOut:function (t, b, c, d) {
		            if ((t /= d / 2) < 1) return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
		            return c / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + b;
		        }
		    },
		    elastic:{
		        easeIn:function (t, b, c, d, a, p) {
		            if (t == 0) return b;
		            if ((t /= d) == 1) return b + c;
		            if (!p) p = d * .3;
		            if (!a || a < Math.abs(c)) {
		                a = c;
		                var s = p / 4;
		            }
		            else var s = p / (2 * Math.PI) * Math.asin(c / a);
		            return -(a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		        },
		        easeOut:function (t, b, c, d, a, p) {
		            if (t == 0) return b;
		            if ((t /= d) == 1) return b + c;
		            if (!p) p = d * .3;
		            if (!a || a < Math.abs(c)) {
		                a = c;
		                var s = p / 4;
		            }
		            else var s = p / (2 * Math.PI) * Math.asin(c / a);
		            return (a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b);
		        },
		        easeInOut:function (t, b, c, d, a, p) {
		            if (t == 0) return b;
		            if ((t /= d / 2) == 2) return b + c;
		            if (!p) p = d * (.3 * 1.5);
		            if (!a || a < Math.abs(c)) {
		                a = c;
		                var s = p / 4;
		            }
		            else var s = p / (2 * Math.PI) * Math.asin(c / a);
		            if (t < 1) return -.5 * (a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		            return a * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5 + c + b;
		        }
		    },
		    back:{
		        easeIn:function (t, b, c, d, s) {
		            if (s == undefined) s = 1.70158;
		            return c * (t /= d) * t * ((s + 1) * t - s) + b;
		        },
		        easeOut:function (t, b, c, d, s) {
		            if (s == undefined) s = 1.70158;
		            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
		        },
		        easeInOut:function (t, b, c, d, s) {
		            if (s == undefined) s = 1.70158;
		            if ((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
		            return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
		        }
		    },
		    bounce:{
		        easeIn:function (t, b, c, d) {
		            return c - Nature.Animation.effects.bounce.easeOut(d - t, 0, c, d) + b;
		        },
		        easeOut:function (t, b, c, d) {
		            if ((t /= d) < (1 / 2.75)) {
		                return c * (7.5625 * t * t) + b;
		            } else if (t < (2 / 2.75)) {
		                return c * (7.5625 * (t -= (1.5 / 2.75)) * t + .75) + b;
		            } else if (t < (2.5 / 2.75)) {
		                return c * (7.5625 * (t -= (2.25 / 2.75)) * t + .9375) + b;
		            } else {
		                return c * (7.5625 * (t -= (2.625 / 2.75)) * t + .984375) + b;
		            }
		        },
		        easeInOut:function (t, b, c, d) {
		            if (t < d / 2) return Nature.Animation.effects.bounce.easeIn(t * 2, 0, c, d) * .5 + b;
		            else return Nature.Animation.effects.bounce.easeOut(t * 2 - d, 0, c, d) * .5 + c * .5 + b;
		        }
		    }
		},
        execute: function(obj){
			if((typeof obj) != "object") throw new Error("In the function 'Nature.Animation.execute', the type of the 'obj' param must be object.");
			this.newInstance(obj)._start();
		}
    }
});