/**
 * 图标按钮类
 * 
 * @iconSrc:
 * @iconWidth:
 * @iconHeight:
 */
Nature.create({
    packages: 'nature.form',
    css: "nature.form.IconButton.css",
    className: 'IconButton',
    extend: "nature.form.FormElement",
    IconButton: function()
    {
        this.dom = Nature.createDom('div', 'icon_button');
        this.attr("id", "icon_btn_" + (Math.round(Math.random() * 10000000)));

        this.iconElement = new Nature.DOMElement(Nature.createDom('div'));
        this.iconElement.attr("id", "icon_" + (Math.round(Math.random() * 10000000)));

        if (this.iconUrl)
        {
            this.setIcon(this.iconUrl);
        }
        else if (this.iconCls)
        {
            this.iconElement.addClass(this.iconCls);
        }
        this.setTitle(this.title);

        // 设置宽高
        this.setWidth(this.width);
        this.setHeight(this.height);

        // 设置行高
        if (typeof this.height == "number" && this.height >= 0)
        {
            this.style("line-height", this.height + "px");
        }

        if (typeof this.click == 'function')
        {
            this.bind('click', this.click);
        }

        this.append(this.iconElement);

        // 渲染
        this.render(this.renderTo);
    },
    setTitle: function(_title)
    {
        if (_title) this.attr("title", _title);
    },
    setIcon: function(iconUrl)
    {
        if (iconUrl) this.iconElement.style("background-image", "url(\"" + iconUrl + "\")");
    }
});