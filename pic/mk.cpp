#include <iostream>
#include <stdio.h>
#include <malloc.h>
#include <conio.h>

using namespace std;

struct Lchar{
	char char_ch;
	struct Lchar *next;
}Lchar,*p,*h,*temp,*top,*base;
char curchar;
char curtocmp;
int righto;
int table[5][8]={{1,0,0,1,0,0},
{0,1,0,0,1,1},
{1,0,0,1,0,0},
{0,1,1,0,1,1},
{1,0,0,1,0,0}};
int i,j;
 
void push(char pchar)
{
temp=(struct Lchar*)malloc(sizeof(Lchar));
temp->char_ch=pchar;
temp->next=top;
top=temp;
}
 
void pop(void)
{
curtocmp=top->char_ch;
if(top->char_ch!='#')
top=top->next;
}
 
void doforpush(int t)
{
switch(t)
{
case 0:push('A');push('T');break;
case 5:push('A');push('T');break;
case 11:push('A');push('T');push('+');break;
 
case 20:push('B');push('F');break;
case 23:push('B');push('F');break;
case 32:push('B');push('F');push('*');break;
 
case 40:push('i');break;
case 43:push(')');push('E');push('(');
}
}
 
void changchartoint()
{
switch(curtocmp)
{
case 'A':i=1;break;
case 'B':i=3;break;
case 'E':i=0;break;
case 'T':i=2;break;
case 'F':i=4;
}
switch(curchar)
{
case 'i':j=0;break;
case '+':j=1;break;
 
case '*':j=2;break;
 
case '(':j=3;break;
case ')':j=4;break;
case '#':j=5;
}
}
 
void dosome(void)
{
int t;
for(;;)
{
pop();
curchar=h->char_ch;
printf("\n%c\t%c",curchar,curtocmp);
if(curtocmp=='#' && curchar=='#')
break;
if(curtocmp=='A'||curtocmp=='B'||curtocmp=='E'||curtocmp=='T'||curtocmp=='F')
{
if(curtocmp!='#')
{
changchartoint();
if(table[i][j])
{
t=10*i+j;
doforpush(t);
continue;
}
else
{
righto=0;
break;
}
}
else
if(curtocmp!=curchar)
{
righto=0;
break;
}
else
break;
}
else
if(curtocmp!=curchar)
{
righto=0;
break;
}
else
{
h=h->next;
continue;
}
}
}
 
void main(void)
{
char ch;
cout<<"* 文件名称: 语法分析"<<endl;
cout<<"   "<<endl;
cout<<"/* 程序相关说明 */"<<endl;
   
       cout<<"---------------------------------------------------------------------"<<endl;
       cout<<"-/* A=E' B=T' */"<<endl;
    cout<<"-* 目    的: 对输入LL(1)文法字符串，本程序能自动判断所给字符串是    -"<<endl;
    cout<<"-*           否为所给文法的句子，并能给出分析过程。                 -"<<endl;
    cout<<"-*-------------------------------------------------------------------"<<endl;
       cout<<"表达式文法为："<<endl;
       cout<<"              E->E+T|T"<<endl;
       cout<<"              T->T*F|F"<<endl;
       cout<<"              F->(E)|i"<<endl;
      
       cout<<"请在下行输入要分析的串(#号结束)："<<endl;
      
righto=1;
base=(struct Lchar*)malloc(sizeof(Lchar));
base->next=NULL;
base->char_ch='#';
temp=(struct Lchar*)malloc(sizeof(Lchar));
temp->next=base;
temp->char_ch='E';
top=temp;
h=(struct Lchar*)malloc(sizeof(Lchar));
h->next=NULL;
p=h;
do{
 
ch=getch();
putch(ch);
if(ch=='i'||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'||ch=='#')
{
temp=(struct Lchar*)malloc(sizeof(Lchar));
temp->next=NULL;
temp->char_ch=ch;
h->next=temp;
h=h->next;
}
else
{
temp=p->next;
printf("\nInput a wrong char!Input again:\n");
for(;;)
{
if (temp!=NULL)
printf("%c",temp->char_ch);
else
break;
temp=temp->next;
}
}
}while(ch!='#');
p=p->next;
h=p;
dosome();
if(righto)
printf("\n成功!\n");
else
printf("\n错误!\n");
getch();
}
