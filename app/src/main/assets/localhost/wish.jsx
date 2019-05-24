const $$=(css,root)=>{
    root=root||document;
    return root.querySelector(css);
};

const e=React.createElement;
var ViewLength;
const javaGetValueEvent=new CustomEvent("javaGetValue");

{
    let gt=function (d2){
        if(this.year>d2.year) return true;
        else if(this.year==d2.year&&this.month>d2.month) return true;
        return false;
    }
    var BeginMonth={year:2019,month:10,gt:gt},EndMonth={year:2023,month:8,gt:gt};
}

//通往java的钥匙
var javaValueLoader=(()=>{
    let V={};
    V.javaReturnVal=getInitialVal();

    V.javaGetMonthVal=(date)=>{
        V.javaReturnVal= {
            date:date,
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        }
        window.dispatchEvent(javaGetValueEvent);
    }
    return V;
})();

//判断某月是否有记录
function hasMonthVal(date){
    if(BeginMonth.gt(date)||!EndMonth.gt(date))
        return false;
    return true;
}

class Root extends React.Component{

    insight(child,offset = 0){ 
        return (child.offsetTop<this.root.current.scrollTop+this.root.current.clientHeight+offset) && (child.offsetTop+child.clientHeight +offset>this.root.current.scrollTop);
    }

    constructor(){
        super();
        this.V=javaValueLoader.javaReturnVal;
        this.root=React.createRef();
        this.beginDiv=React.createRef();
        this.endDiv=React.createRef();

        this.state={
            monthes:this.V,
            beginDivOff:false,
            endDivOff:false,
        }

        this.i=0;
    }

    componentDidMount(){
        //使用onscroll事件判断屏幕是否划到两端
        this.scrollFlag=false;//锁1（伪），用于优化onscroll性能
        this.lock=false;//锁2，用于异步加载java
        this.root.current.onscroll=()=>{this.scrollHandler()};//rootDiv滚动事件
        this.scrollInterval=setInterval(()=>{

            if(!this.scrollFlag)
                return;
            this.scrollFlag=false;

            if(this.lock==true)
                return;
            this.lock=true;
                if(this.beginDiv.current&&this.insight(this.beginDiv.current)){ //名为startdiv的块处于视野 加载上月值
                    let targetMonth=preMonth(this.getBeginDate());//begin
                    if(hasMonthVal(targetMonth)){
                        this.beforeScroll={direction:"begin"};//begin
                        new Promise((resolve,reject)=>{
                            this.listener=()=>{resolve()};
                            window.addEventListener("javaGetValue", this.listener);//监听java返回值
                            javaValueLoader.javaGetMonthVal(targetMonth);//call java
                        }).then(()=>{
                            window.removeEventListener("javaGetValue",this.listener);
                            this.setState((state)=>({
                                monthes:[javaValueLoader.javaReturnVal,...state.monthes],//begin
                            }));
                        });
                    }
                    else{
                        this.setState(()=>({beginDivOff:true}));
                    }

                    return;
                }
                if(this.endDiv.current&&this.insight(this.endDiv.current)){ //名为enddiv的块处于视野 加载下月值
                    let targetMonth=nextMonth(this.getEndDate());//end
                    if(hasMonthVal(targetMonth)){
                        this.beforeScroll={direction:"end"};//end
                        new Promise((resolve,reject)=>{
                            this.listener=()=>{resolve()};
                            window.addEventListener("javaGetValue", this.listener);
                            javaValueLoader.javaGetMonthVal(targetMonth);
                        }).then(()=>{
                            window.removeEventListener("javaGetValue",this.listener);
                            this.setState((state)=>({
                                monthes:[...state.monthes,javaValueLoader.javaReturnVal],//end
                            }));
                        });
                    }
                    else{
                        this.setState(()=>({endDivOff:true}));
                    }

                    return;
                }
            this.lock=false;
        },50);
    }

    scrollHandler(){
       this.scrollFlag=true;console.log("scrolled");
    }

    getBeginDate=()=>{
        if(this.state.monthes.length==0) 
            return null;
        return this.state.monthes[0].date;
    }

    getEndDate=()=>{
        if(this.state.monthes.length==0) 
            return null;
        return this.state.monthes[this.state.monthes.length-1].date;
    }

    getSnapshotBeforeUpdate(){
        if(this.lock==false)
            return null;
        return {scrollHeight:this.root.current.scrollHeight,scrollTop:this.root.current.scrollTop}
    }

    componentDidUpdate(preProps,preState,snapshot){
        this.i++;

        if(this.lock==false)
            return;

        if(this.beforeScroll.direction=="begin"){
            this.root.current.scrollTop=snapshot.scrollTop -snapshot.scrollHeight + this.root.current.scrollHeight;
            console.log(this.i+" " + this.root.current.scrollTop);
        }
        this.lock=false;

          //获取数据完成，解除锁2
    }

    render(){
        let beginDiv=this.state.beginDivOff ? null : <div id="beginDiv" ref={this.beginDiv}></div>;
        let endDiv=this.state.endDivOff ? null : <div id="endDiv" ref={this.endDiv}></div>;

        return(
            <div id="sub-root" ref={this.root}>
                {beginDiv}
                {
                    this.state.monthes.map((val,idx)=>{
                        let date=val.date;
                        return(
                            <Month {...val} key={date.year+""+date.month} />
                        )
                    })
                }
                {endDiv}
            </div>
        );
    }
}

class Month extends React.Component{
    render(){

        return(
            <div className="month">
                <div className="sobar">
                    <div className="month-label">{this.props.date.year+"-"+this.props.date.month}</div>
                </div>

                <div className="item-list">
                    {this.props.item.map((val,idx)=>{
                        return <ItemDiv {...val} key={idx} />
                    })}
                </div>
            </div>
        );
    }
}

class ItemDiv extends React.Component{
    render(){
        return(
            <div className="item">
                {this.props.name + " " + this.props.state}
            </div>
        );
    }
}

window.onload=()=>{
    let fetchJsonPromise=new Promise((resolve,reject)=>{
        let i=setInterval(()=>{
            if(javaState=="resolved")
                resolve();
            clearInterval(i);
        },50);
    });
    fetchJsonPromise.then(()=>{
        ReactDOM.render(<Root />,root);
    })

    //emit
    javaState="resolved";
}

/** 根据页面高度动态计算应该显示的月份数
 *  CSS设置的每月最短是150px
 *  默认最小是6 （的2倍）
 */
function calculateVLength(){
    let t=Math.ceil(window.innerHeight/150.0);
    ViewLength=t>6?t:6;
    console.log(ViewLength);
}

function preMonth(ym){
    let {year,month}=ym;
    if(month==1) 
        return {year:year-1,month:12};
    else 
        return {year:year,month:month-1};
}

function nextMonth(ym){
    let {year,month}=ym;
    if(month==12) 
        return {year:year+1,month:1};
    else 
        return {year:year,month:month+1};
}



var javaState;
/*
[pending(进行中),resolved(完成)]
*/

/**
 * TEST
 */
function getInitialVal(){
    return [
        {
            date:{year:2021,month:12},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        },
        {
            date:{year:2022,month:1},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        },
        {
            date:{year:2022,month:2},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        },
        {
            date:{year:2022,month:3},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        },
        {
            date:{year:2022,month:4},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        },
        {
            date:{year:2022,month:5},
            item:[
                { name:"item1", state:"resolved"},
                { name:"item2", state:"resolved"},
            ]
        }
    ];
}