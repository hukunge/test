package com.example.leandro.betterstack;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Leandro Ruiz on 07/01/2016.
 * A new (and better) implementation for multiple stacks. This stack manager saves fragment's class
 * and it's current state (if the fragment implements {@link com.example.leandro.betterstack.StackInfo this interface}
 * instead of a complete fragment, which probably cause dangling references or exceptions, especially if you're
 * handling nested fragments (like {@link android.support.v4.view.ViewPager ViewPager} usually does).
 * It also reduces a fairly amount of memory used since it doesn't stores entire fragments with almost
 * no noticeable recoil on CPU usage. So why don't you give it a try?
 */
public class BetterStack {
    private HashMap<String, Stack<ItemStack>> stacks;
    private List<String> keys;
    private String currentStack;
    private Fragment currentFragment;
    private FragmentManager manager;
    private int containerId;

    /**
     * Constructor for this class.
     *
     * @param manager     An according fragment manager, (child manager if stack is nested)
     * @param containerId The resource id for the layout or view to display the fragments
     * @param stackNames  A list with the stack's tags
     */
    public BetterStack(FragmentManager manager, int containerId, List<String> stackNames) {
        this.manager = manager;
        this.stacks = new HashMap<>();
        this.containerId = containerId;
        this.keys = stackNames;

        for (String s : stackNames) {
            stacks.put(s, new Stack<ItemStack>());
        }
    }

    private void changeVisibleFragmentTo(Fragment f) {
        manager.beginTransaction()
                .replace(containerId, f)
                .commit();
    }

    /**
     * Switches to the stack with the name stackTag. If no fragment is being displayed,
     * you should call this to set current stack and fragment.
     *
     * @param stackTag A tag associated to an stack
     * @throws IllegalArgumentException if the stack doesn't exist or is empty
     */
    public void switchToStack(String stackTag) {
        if (stacks.containsKey(stackTag)) {
            if (stacks.get(stackTag).empty()) {
                throw new IllegalArgumentException("Stack is empty");
            }

            //先保存当前fragment
            if (currentFragment != null) {
                Map<String, Object> info;
                info = currentFragment instanceof StackInfo
                        ? ((StackInfo) currentFragment).saveState()
                        : new HashMap<String, Object>();

                stacks.get(currentStack).peek().setInfo(info);
            }

            //设置stackTag为当前
            currentStack = stackTag;

            //切换到stackTag所在的栈
            ItemStack item = stacks.get(currentStack).peek();
            Fragment fragment = item.recreate();

            currentFragment = fragment;
            //显示该栈第一个fragment
            changeVisibleFragmentTo(fragment);
        } else {
            throw new IllegalArgumentException("Stack doesn't exist");
        }
    }

    /**
     * Push fragment to current stack and then display it in container. If the currently displayed
     * fragment implements {@link com.example.leandro.betterstack.StackInfo StackInfo}, it will save
     * it's state before being stacked.
     *
     * @param fragment The fragment to push
     */
    public void pushCurrent(Fragment fragment) {
        Map<String, Object> info;

        //保存上一个fragment
        if (currentFragment instanceof StackInfo) {
            info = ((StackInfo) currentFragment).saveState();
            stacks.get(currentStack).peek().setInfo(info);
        }

        //把fragment压入栈
        ItemStack toPush = new ItemStack(fragment.getClass(), new HashMap<String, Object>());
        stacks.get(currentStack).push(toPush);

        //显示该fragment
        currentFragment = fragment;
        changeVisibleFragmentTo(currentFragment);
    }

    /**
     * Display the fragment in container; if addToStack is set true, it will also be pushed on the
     * current stack. If the currently displayed fragment implements
     * {@link com.example.leandro.betterstack.StackInfo StackInfo}, it will save it's state before
     * beign stacked.
     *
     * @param fragment   The fragment to push
     * @param addToStack If true, the fragment will be stacked
     */
    public void pushCurrent(Fragment fragment, boolean addToStack) {
        if (addToStack)
            pushCurrent(fragment);
        else
            changeVisibleFragmentTo(fragment);
    }

    /**
     * Pop current fragment from stack and display the next. It won't pop the first fragment (all
     * stacks must have at least one element).
     *
     * @return true if pop was successful
     */
    public boolean popCurrent() {
        if (stacks.get(currentStack).size() == 1)
            return false;

        //弹出最上层一个fragment
        stacks.get(currentStack).pop();
        //recreate下一个fragment
        Fragment f = stacks.get(currentStack).peek().recreate();
        //显示下一个fragment
        currentFragment = f;
        changeVisibleFragmentTo(f);

        return true;
    }

    /**
     * Pop all fragments from current stack and display the first one. It won't pop the first
     * fragment (all stacks must have at least one element).
     */
    public void popCurrentAll() {
        Fragment f;
        //拿到第一个ItemStack
        ItemStack first = stacks.get(currentStack).firstElement();

        //清除栈里所有的ItemStack
        stacks.get(currentStack).clear();

        //创建第一个，并显示
        f = first.recreate();
        stacks.get(currentStack).push(first);
        changeVisibleFragmentTo(f);
    }

    /**
     * Push a fragment to stack named tag. It won't be displayed in container. You should use this
     * after calling {@link com.example.leandro.betterstack.BetterStack#BetterStack(FragmentManager, int, List)}
     * to add each stack's first element.
     *
     * @param fragment The fragment to be pushed
     * @param tag      The stack's tag where the fragment will be pushed
     * @throws IllegalArgumentException if the stack doesn't exists
     */
    public void addToStack(Fragment fragment, String tag) {
        if (!stacks.containsKey(tag))
            throw new IllegalArgumentException("Stack doesn't exist");

        Map<String, Object> info = new HashMap<>();
        if (fragment instanceof StackInfo) {
            info = ((StackInfo) fragment).saveState();
        }
        ItemStack toPush = new ItemStack(fragment.getClass(), info);
        stacks.get(tag).push(toPush);
    }
}
