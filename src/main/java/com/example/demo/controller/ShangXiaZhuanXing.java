package com.example.demo.controller;
/**
 * 先向上转型再强制向下转型后还是能用自己的方法的，知识不要乱转型得对应（人向上转后向下还要转成人）
 * @author JD-DZ327
 *
 */
public class ShangXiaZhuanXing {
	public static void main(String[] args) {
			Person person=new Person();
			new ShangXiaZhuanXing().test(person);
	}
	public void test(Object o) {
		Person p=(Person)o;
		p.eat();
	}
}
class Person{
	public void eat() {
		System.out.println("吃");
	}
}