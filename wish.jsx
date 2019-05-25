const $$ = (css, root) => {
    root = root || document;
    return root.querySelector(css);
};

const e = React.createElement;
var ViewLength;
const javaGetValueEvent = new CustomEvent("javaGetValue");

//从java获取起始与结束的月份
var BeginMonth = { year: 2019, month: 10 }, EndMonth = { year: 2023, month: 8 };

//通往java的钥匙
var javaDataLoader = (() => {
    let V = {};
    V.lock = false;

    //TODO 从java获取值
    V.javaGetMonthVal = (date, length = 1) => {
        if (length <= 1) {
            V.javaReturnVal = [{
                date: date,
                item: [
                    { name: "item1", state: "resolved" },
                    { name: "item2", state: "resolved" },
                    { name: "item2", state: "resolved" },
                    { name: "item2", state: "resolved" },
                    { name: "item2", state: "resolved" },
                    { name: "item2", state: "resolved" },
                ]
            }]
        }
        else {
            V.javaReturnVal =  getInitialVal();
        }

        window.dispatchEvent(javaGetValueEvent);
    }
    return V;
})();

//判断某月是否有记录
function hasMonthVal(date) {
    if (ge(BeginMonth, date) || ge(date, EndMonth))
        return false;
    return true;

    function ge(d1, d2) {
        if (d1.year > d2.year) return true;
        else if (d1.year == d2.year && d1.month >= d2.month) return true;
        return false;
    }
}

class Root extends React.Component {

    insight(child, offset = 0) {
        return (child.offsetTop < this.root.scrollTop + this.root.clientHeight + offset) && (child.offsetTop + child.clientHeight + offset > this.root.scrollTop);
    }

    constructor() {
        super();
        this.V = javaDataLoader.javaReturnVal;
        this.beginDiv = React.createRef();
        this.endDiv = React.createRef();

        this.state = {
            monthes: this.V,
            beginDivOff: false,
            endDivOff: false,
        }

        this.i = 0;
    }

    componentDidMount() {
        this.root = $$("#root");
        this.root.scrollTop = 152;
        //使用onscroll事件判断屏幕是否划到两端
        this.scrollFlag = false;//主要使用定时器触发加载而非滚轮事件，onscroll事件太过频繁

        this.root.onscroll = () => { this.scrollHandler() };//rootDiv滚动事件
        this.scrollInterval = setInterval(() => {
            console.log(this.root.scrollTop);
            if (!this.scrollFlag)
                return;
            this.scrollFlag = false;

            if (javaDataLoader.lock == true)
                return;
            javaDataLoader.lock = true;
            //TODO 是否使用React ref替换DOM和魔法值
            if (this.beginDiv.current && this.insight({ offsetTop: 0, clientHeight: 150 })) { //名为startdiv的块处于视野 加载上月值
                let targetMonth = preMonth(this.getBeginDate());//begin
                if (hasMonthVal(targetMonth)) {
                    this.beforeScroll = { direction: "begin" };//begin
                    new Promise((resolve, reject) => {
                        this.listener = () => { resolve() };
                        window.addEventListener("javaGetValue", this.listener);//监听java返回值
                        javaDataLoader.javaGetMonthVal(targetMonth);//call java
                    }).then(() => {
                        window.removeEventListener("javaGetValue", this.listener);
                        this.setState((state) => ({
                            monthes: [...javaDataLoader.javaReturnVal, ...state.monthes],//begin
                        }));
                    });
                }
                else {
                    this.setState(() => ({ beginDivOff: true }));
                }
                return;
            }
            if (this.endDiv.current && this.insight({ offsetTop: this.root.scrollHeight - 150, clientHeight: 150 })) { //名为enddiv的块处于视野 加载下月值
                let targetMonth = nextMonth(this.getEndDate());//end
                if (hasMonthVal(targetMonth)) {
                    this.beforeScroll = { direction: "end" };//end
                    new Promise((resolve, reject) => {
                        this.listener = () => { resolve() };
                        window.addEventListener("javaGetValue", this.listener);
                        javaDataLoader.javaGetMonthVal(targetMonth);
                    }).then(() => {
                        window.removeEventListener("javaGetValue", this.listener);
                        this.setState((state) => ({
                            monthes: [...state.monthes, ...javaDataLoader.javaReturnVal],//end
                        }));
                    });
                }
                else {
                    this.setState(() => ({ endDivOff: true }));
                }
                return;
            }
            javaDataLoader.lock = false;
        }, 50);
    }

    scrollHandler() {
        this.scrollFlag = true;
        console.log("scrolled" + this.root.scrollTop);
    }

    getBeginDate = () => {
        if (this.state.monthes.length == 0)
            return null;
        return this.state.monthes[0].date;
    }

    getEndDate = () => {
        if (this.state.monthes.length == 0)
            return null;
        return this.state.monthes[this.state.monthes.length - 1].date;
    }

    getSnapshotBeforeUpdate() {
        if (javaDataLoader.lock == false)
            return null;
        return { scrollHeight: this.root.scrollHeight, scrollTop: this.root.scrollTop }
    }

    componentDidUpdate(preProps, preState, snapshot) {
        this.i++;

        if (javaDataLoader.lock == false)
            return;
        //重置视口位置以防止新加载的数据把旧数据挤出视口
        if (this.beforeScroll.direction == "begin") {
            this.root.scrollTop = snapshot.scrollTop - snapshot.scrollHeight + this.root.scrollHeight;
            this.savedScrollTop = this.root.scrollTop;
            console.log("did   " + this.root.scrollTop);
        }
        javaDataLoader.lock = false; //获取数据完成，解除锁2
    }

    render() {
        let beginDiv = this.state.beginDivOff ? null : <div id="beginDiv" ref={this.beginDiv}></div>;
        let endDiv = this.state.endDivOff ? null : <div id="endDiv" ref={this.endDiv}></div>;

        return (
            <div id="sub-root" >
                {beginDiv}
                {
                    this.state.monthes.map((val, idx) => {
                        let date = val.date;
                        return (
                            <Month {...val} key={date.year + "" + date.month} />
                        )
                    })
                }
                {endDiv}
            </div>
        );
    }
}

class Month extends React.Component {
    render() {
        return (
            <div className="month">
                <div className="sobar">
                    <div className="month-label">{this.props.date.year + "-" + this.props.date.month}</div>
                </div>

                <div className="item-list">
                    {this.props.item.map((val, idx) => {
                        return <ItemDiv {...val} key={idx} />
                    })}
                </div>
            </div>
        );
    }
}

class ItemDiv extends React.Component {
    render() {
        let className = this.props.state;

        return (
            <div className={"item " + className} onClick={() => { alert(this.props.name) }}>
                {this.props.name + " " + this.props.state}
            </div>
        );
    }
}

window.onload = () => {
    let listener;
    new Promise((resolve, reject) => {
        listener = () => { resolve() };
        window.addEventListener("javaGetValue", listener);
        javaDataLoader.javaGetMonthVal({year:2021,month:12},6);
    }).then(() => {
        window.removeEventListener("javaGetValue", listener);
        ReactDOM.render(<Root />, root);
    })

}

/** 根据页面高度动态计算应该显示的月份数
 *  CSS设置的每月最短是150px
 *  默认最小是6 （的2倍）
 */
function calculateVLength() {
    let t = Math.ceil(window.innerHeight / 150.0);
    ViewLength = t > 6 ? t : 6;
    console.log(ViewLength);
}

function preMonth(ym) {
    let { year, month } = ym;
    if (month == 1)
        return { year: year - 1, month: 12 };
    else
        return { year: year, month: month - 1 };
}

function nextMonth(ym) {
    let { year, month } = ym;
    if (month == 12)
        return { year: year + 1, month: 1 };
    else
        return { year: year, month: month + 1 };
}


/**
 * TEST
 */
function getInitialVal() {
    return [
        {
            date: { year: 2021, month: 12 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "cancelled" },
                { name: "item2", state: "cancelled" },
                { name: "item2", state: "resolved" },
                { name: "item2", state: "onprogress" },
            ]
        },
        {
            date: { year: 2022, month: 1 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "resolved" },
            ]
        },
        {
            date: { year: 2022, month: 2 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "resolved" },
            ]
        },
        {
            date: { year: 2022, month: 3 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "resolved" },
            ]
        },
        {
            date: { year: 2022, month: 4 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "resolved" },
                { name: "item2", state: "resolved" },
                { name: "item2", state: "resolved" },
                { name: "item2", state: "resolved" },
                { name: "item2", state: "resolved" },
            ]
        },
        {
            date: { year: 2022, month: 5 },
            item: [
                { name: "item1", state: "resolved" },
                { name: "item2", state: "resolved" },
            ]
        }
    ];
}