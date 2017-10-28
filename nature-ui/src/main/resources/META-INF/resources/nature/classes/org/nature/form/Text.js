/**
 * 图标按钮类
 * iconClass:
 * iconWidth: 
 * iconHeight: 
 * horizontalPadding:
 * verticalPadding:
 * 定义气泡提示来实现用户的友好提示
 */
Nature.create({
	packages: 'org.nature.form',
	className: 'Text',
	extend: 'org.nature.form.FormElement',
	Text: function()
	{
		this.dom = Nature.createDom('input', "common_input");
		this.dom.type = 'text';
		
		// 设置宽高
		this.setWidth(this.width);
		this.setHeight(this.height);
		
		this.setListeners(this.listeners);
		
		this.bind("keyup", function(){
			
		});
		
		// 设置初始值
		this.setValue(this.value != undefined ? this.value : "");
		
		// 渲染到指定容器
		this.render(this.renderTo);
	}
});