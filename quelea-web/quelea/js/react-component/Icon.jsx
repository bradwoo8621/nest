/**
 * icon based on font-awesome
 */
var Icon = React.createClass({
    propTypes: {
        size: React.PropTypes.oneOf(["lg", "2x", "3x", "4x", "5x"]),
        fixWidth: React.PropTypes.bool,

        icon: React.PropTypes.string.isRequired,
        spin: React.PropTypes.bool,
        pulse: React.PropTypes.bool,
        rotate: React.PropTypes.oneOf([90, 180, 270]),
        flip: React.PropTypes.oneOf(["h", "v"]),
        iconClassName: React.PropTypes.string,

        backIcon: React.PropTypes.string,
        backSpin: React.PropTypes.bool,
        backPulse: React.PropTypes.bool,
        backRotate: React.PropTypes.oneOf([90, 180, 270]),
        backFlip: React.PropTypes.oneOf(["h", "v"]),
        backClassName: React.PropTypes.string
    },
    getDefaultProps: function () {
        return {
            fixWidth: false,
            spin: false
        };
    },
    /**
     * get size
     * @returns {}
     */
    getSize: function () {
        return {
            "fa-lg": this.props.size === "lg",
            "fa-2x": this.props.size === "2x",
            "fa-3x": this.props.size === "3x",
            "fa-4x": this.props.size === "4x",
            "fa-5x": this.props.size === "5x",
            "fa-fw": this.props.fixWidth
        };
    },
    /**
     * get icon
     * @returns {*}
     */
    getIcon: function () {
        var c = {
            "fa": true,
            "fa-spin": this.props.spin,
            "fa-pulse": this.props.backPulse,
            "fa-rotate-90": this.props.backRotate == 90,
            "fa-rotate-180": this.props.backRotate == 180,
            "fa-rotate-270": this.props.backRotate == 270,
            "fa-flip-horizontal": this.props.backFlip === "h",
            "fa-flip-vertical": this.props.backFlip === "v"
        };
        c["fa-" + this.props.icon] = true;
        if (this.props.iconClassName) {
            c[this.props.iconClassName] = true;
        }
        return c;
    },
    /**
     * get background icon
     * @returns {*}
     */
    getBackIcon: function () {
        var c = {
            "fa": true,
            "fa-spin": this.props.backSpin,
            "fa-pulse": this.props.pulse,
            "fa-rotate-90": this.props.rotate == 90,
            "fa-rotate-180": this.props.rotate == 180,
            "fa-rotate-270": this.props.rotate == 270,
            "fa-flip-horizontal": this.props.flip === "h",
            "fa-flip-vertical": this.props.flip === "v"
        };
        c["fa-" + this.props.backIcon] = true;
        if (this.props.backClassName) {
            c[this.props.backClassName] = true;
        }
        return c;
    },
    /**
     * render
     * @returns {XML}
     */
    render: function () {
        var size = this.getSize();
        var iconClasses = this.getIcon();
        if (this.props.backIcon) {
            size["fa-stack"] = true;
            var backIconClasses = this.getBackIcon();
            return (<span className={size}>
                <i className={iconClasses}/>
                <i className={backIconClasses}/>
            </span>);
        }
        $.extend(iconClasses, size);
        return <span className={React.addons.classSet(iconClasses)}/>;
    }
});