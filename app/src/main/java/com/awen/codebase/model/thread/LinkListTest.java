package com.awen.codebase.model.thread;

import android.util.Log;

public class LinkListTest {

    class ListNode{
        int val;
        ListNode next;
    }

    public ListNode deleteSameNodeDigui(ListNode head){
        if(head == null){
            return null;
        }
        if(head.next!=null&&head.val == head.next.val){
            head = head.next;
        }else{
            head.next = deleteSameNode(head.next);
        }
        return head;
    }

    private ListNode deleteSameNode(ListNode head){
        if(head == null){
            return null;
        }
        ListNode p,q,temp;
        p = temp = head;
        q = head.next;
        while (q!=null){
            if(p.val == q.val){
                q = q.next;
                temp.next = q;
            }else{
                p = temp = q;
                q = q.next;
            }
        }
        return head;
    }

    private ListNode deleteListNode(ListNode head,int value){
        if(head == null){
            return null;
        }
        if(head.val == value){
            head = head.next;
        }else{
            head.next = deleteListNode(head.next,value);
        }
        return head;
    }

    public void startTest(){
        ListNode head = new ListNode();
        head.val = 1;
        head.next = new ListNode();

        head.next.val = 2;
        head.next.next = new ListNode();

        head.next.next.val = 2;
        head.next.next.next = new ListNode();

        head.next.next.next.val = 3;
        head.next.next.next.next = new ListNode();

        head.next.next.next.next.val = 3;
        head.next.next.next.next.next = new ListNode();

        head.next.next.next.next.next.val = 4;

        ListNode p = head;
        while (p!=null){
            Log.i("ListNode","原始链表值:"+p.val);
            p = p.next;
        }
        ListNode q = deleteListNode(head,3);
        while (q!=null){
            Log.i("ListNode","链表值："+q.val);
            q = q.next;
        }

    }

}
