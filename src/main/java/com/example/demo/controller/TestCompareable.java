
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.example.demo.entity.Emp;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年8月13日 上午11:25:21

* 类说明 treeset底层红黑树，会用到compareTo方法,比较为0的话就会覆盖了，compareTo比较的原理是比较ASCII码

*/

public class TestCompareable implements Comparable<TestCompareable>{

	public String name;
	public int age;
	/**
	 * 比如对对象用年龄进行排序，年龄一样用名字排序
	 */
	@Override
	public int compareTo(TestCompareable t) {
		int num=((Integer)this.age).compareTo(t.age);
		return num==0?this.name.compareTo(t.name):num;
	}

	public TestCompareable(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	public static void main(String[] args) {
		Set<TestCompareable> set=new TreeSet<TestCompareable>();
		TestCompareable t=new TestCompareable("a",1);
		TestCompareable t1=new TestCompareable("b",22);
		System.out.println("t的hashcode"+t.hashCode());
		System.out.println("t1的hashcode"+t1.hashCode());
		TestCompareable t2=new TestCompareable("c",3);
		TestCompareable t3=new TestCompareable("a",2);
		TestCompareable t4=new TestCompareable("b",2);
		set.add(t);
		set.add(t1);
		set.add(t2);
		set.add(t3);
		set.add(t4);
		for (TestCompareable testCompareable : set) {
			System.out.println(testCompareable.name+"   "+testCompareable.age);
		}
		/*set.forEach(new Consumer() {
			@Override
			public void accept(Object t) {
				System.out.println(t);
			}
		});*/
		/**
		 * forEach（）方法是Iterable<T>接口中的一个方法。Java容器中，
		 * 所有的Collection子类（List、Set）会实现Iteratable接口以实现foreach功能。
		 * forEach（）方法里面有个Consumer类型，它是Java8新增的一个消费型函数式接口，
		 * 其中的accept(T t)方法代表了接受一个输入参数并且无返回的操作。
		 */
		set.forEach(x->System.out.println(x));
		//map集合不属于Collection，它有自己的foreach()方法：
        Map<String,User> map =new HashMap<>();
        map.put("1",new User("aa",10));
        map.put("2",new User("bb",11));
        map.put("3",new User("cc",12));

        //匹配输出，匹配项可以为list集合元素的属性（成员变量）
       // (k,v)-> System.out.println("v="+k+",v="+v)
        map.forEach((a,v)->System.out.println("id : " + a + " User : " + v));
       /**
        *  Collections.sort(list)；会自动调用compareTo，
        *  如果没有实现Comparable，list是不会排序的，也不会调用compareTo方法。
        *  https://www.cnblogs.com/lukelook/articles/11101366.html里面有sort的例子
        */
        //Collections.sort(list);
        //Collections.sort(list,(o1,o2)->{return o1.compareTo(o2);});

        List<String> list=new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);
        list.add(3,"位置一");
        System.out.println(list);

        TreeSet<User> treeSet = new TreeSet<User>(new User());
        User user=new User("a",1);
        User user1=new User("b",2);
        User user2=new User("c",3);
        User user3=new User("a",2);
        User user4=new User("b",3);
        treeSet.add(user);
        treeSet.add(user1);
        treeSet.add(user2);
        treeSet.add(user3);
        treeSet.add(user4);
        for (User user41 : treeSet) {
			System.out.println(user41.name+"   "+user41.age);
		}
	}

}
class User implements Comparator<User>{
	String name;
	int age;
	public User() {

	}
	public User(String name,int age) {
		this.name=name;
		this.age=age;
}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * 重写的方法，比较年龄,按年龄排序
	 * @param s1
	 * @param s2
	 * @return
	 */
	@Override
	public int compare(User s1, User s2) {
		 int num = s1.getName().compareTo(s2.getName());
		 return num==0?new Integer(s1.getAge()).compareTo(new Integer(s2.getAge())):num;
	}
}