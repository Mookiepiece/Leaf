const $$ = (css, root) => {
    root = root || document;
    return root.querySelector(css);
};

const e = React.createElement;
var ViewLength;
const javaGetValueEvent = new CustomEvent("javaGetValue");

//从java获取起始与结束的月份
var FirstWishStartTime, LastWishEndTime;

//模拟JAVA暴露的对象
var java={
    getBeginAndEndMonth:()=>{
        javaDataReceiver.getBeginAndEndMonth('{"FirstWishStartTime":{"year":2019,"month":10},"LastWishEndTime":{"year":2023,"month":8}}');
    },
    javaGetMonthVal:(json)=>{
        let jj=JSON.parse(json);
        let r=[];
        for(let i=0;i<jj.length;i++){
            let a={"date":{"year":2021,"month":12},"item":[{"name":"item1","state":2},{"name":"item2","state":1},{"name":"item2","state":1},{"name":"item2","state":2},{"name":"item2","state":0}]}
            let d=jj;
            for(let j=0;j<i;j++)
                d=preMonth(d);
            a.date=d;
            r.unshift(a);
        }
        javaDataReceiver.javaGetMonthVal(JSON.stringify(r));
    },
    loadMonth:(json)=>{
        let jj=JSON.parse(json);
        let r={"date":{"year":jj.year,"month":jj.month},"item":[{"name":"item1","state":2},{"name":"item dad dettr ex2","state":1},{"name":"item2 trwt","state":1},{"name":"item2","state":2},{"name":"item2","state":0}]}
        r=[r];
        javaDataReceiver.javaGetMonthVal(JSON.stringify(r));
    }
};


//通往java的钥匙
var javaDataReceiver = (() => {
    let V = {};
    V.lock = false;

    V.getBeginAndEndMonth = (str) => {
         let json= JSON.parse(str);
         FirstWishStartTime=json.FirstWishStartTime;
         LastWishEndTime=json.LastWishEndTime;
    }

    //TODO 从java获取值
    V.javaGetMonthVal = (str) => {
        V.javaReturnVal = JSON.parse(str);
        window.dispatchEvent(javaGetValueEvent);
    }
    return V;
})();

window.onload = () => {
    calculateVLength();

    //第一步获取最开始和最晚的月份
    let a;
    java.getBeginAndEndMonth();
    a = setInterval(()=>{
        if(FirstWishStartTime!=null){
            window.clearInterval(a);
            initData();
        }
    },50)

    /** 根据页面高度动态计算初始显示的月份数
     *  CSS设置的每月最短是150px
     *  默认最小是6
     */
    function calculateVLength() {
        let t = Math.ceil(window.innerHeight / 150.0);
        ViewLength = t > 6 ? t : 6;
        console.log(ViewLength);
    }
}


//加载很多很多个月份的数据
function initData(){
    if(javaDataReceiver.lock)
        return;
    javaDataReceiver.lock=true;
    let listener;
    new Promise((resolve, reject) => {
        listener = () => { resolve() };
        window.addEventListener("javaGetValue", listener);
        java.javaGetMonthVal(JSON.stringify({ year: 2021, month: 12,length:ViewLength}));
    }).then(() => {
        window.removeEventListener("javaGetValue", listener);
        ReactDOM.render(<Root />, root);
        javaDataReceiver.lock=false;
    })
}


//判断某月是否有记录
function avalialeMonth(date) {
    if (ge(FirstWishStartTime, date) || ge(date, LastWishEndTime))
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
        this.V = javaDataReceiver.javaReturnVal;
        this.beginDiv = React.createRef();
        this.endDiv = React.createRef();
        this.beforeScroll={};
        
        if(this.V.length<ViewLength){
            this.state = {
                monthes: this.V,
                beginDivOff: true,
                endDivOff: true,
            }
        }
        else{
            this.state = {
                monthes: this.V,
                beginDivOff: false,
                endDivOff: false,
            }
        }


        this.i = 0;
    }

    componentDidMount() {
        this.root = $$("#root");
        this.root.scrollTop = 152;

        this.root.onscroll = () => { this.scrollHandler() };//rootDiv滚动事件
    }

    scrollHandler() {

        //上锁
        if (javaDataReceiver.lock == true)
            return;
        javaDataReceiver.lock = true;

        //判断视口滚动到头,以加载上/下个月的数据
        let flag = 0;
        if (this.beginDiv.current && this.insight({ offsetTop: 0, clientHeight: 150 })) flag = 1;
        if (this.endDiv.current && this.insight({ offsetTop: this.root.scrollHeight - 150, clientHeight: 150 })) flag = 2;
        if (flag != 0) {
            let targetMonth;  //上/下个月
            targetMonth = (flag == 1 ? preMonth(this.getDataBeginDate()) : nextMonth(this.getDataEndDate()));
            if (avalialeMonth(targetMonth)) { //判断上/下个月是否到头 到头就触发不能滚动
                flag == 1 ? this.beforeScroll = { direction: "begin" } : this.beforeScroll = { direction: "end" };
                
                //异步操作:读取JAVA数据
                new Promise((resolve, reject) => {
                    this.listener = () => { resolve() };
                    window.addEventListener("javaGetValue", this.listener);//监听java返回值
                    java.loadMonth(JSON.stringify(targetMonth));//call java
                }).then(() => {
                    window.removeEventListener("javaGetValue", this.listener);
                    if (flag == 1)
                        this.setState((state) => ({
                            monthes: [...javaDataReceiver.javaReturnVal, ...state.monthes],
                        }))
                    else
                        this.setState((state) => ({
                            monthes: [...state.monthes, ...javaDataReceiver.javaReturnVal],
                        }));
                });
            }
            else {
                if (flag == 1)
                    this.setState(() => ({ beginDivOff: true }));
                else
                    this.setState(() => ({ endDivOff: true }));
            }
            return;
        }

        javaDataReceiver.lock = false;
    }

    //遍历数组找最早月
    getDataBeginDate = () => {
        if (this.state.monthes.length == 0)
            return null;
        return this.state.monthes[0].date;
    }

    //遍历数组找最晚月
    getDataEndDate = () => {
        if (this.state.monthes.length == 0)
            return null;
        return this.state.monthes[this.state.monthes.length - 1].date;
    }

    getSnapshotBeforeUpdate() {
        if (javaDataReceiver.lock == false)
            return null;
        return { scrollHeight: this.root.scrollHeight, scrollTop: this.root.scrollTop }
    }

    componentDidUpdate(preProps, preState, snapshot) {
        this.i++;

        if (javaDataReceiver.lock == false)
            return;
        //重置视口位置以防止新加载的数据把旧数据挤出视口
        if (this.beforeScroll.direction == "begin") {
            this.root.scrollTop = snapshot.scrollTop - snapshot.scrollHeight + this.root.scrollHeight;
        }
        javaDataReceiver.lock = false; //获取数据完成，解除锁
    }

    render() {
        let beginDiv = this.state.beginDivOff ? null : <div id="beginDiv" ref={this.beginDiv}></div>;
        let endDiv = this.state.endDivOff ? null : <div id="endDiv" ref={this.endDiv}></div>;

        return (
            <div id="sub-root">
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
        let color;
        switch(this.props.state){
            case 0:
                color=" mdui-color-blue mdui-text-color-white-text"; break;
            case 1:
                color=" mdui-color-pink-50 mdui-text-color-black-secondary"; break;
            case 2:
                color=" mdui-color-green-300 mdui-text-color-white-text"; break;
        }

        return (
            <button className={"mdui-btn mdui-ripple item " + color} onClick={() => { alert(this.props.name) }} >
                {this.props.name + " " + this.props.state}
            </button>
            
        /*<div className={"item " + className} }>
                        
        </div>*/
        );
    }
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

