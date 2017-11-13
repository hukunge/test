# BetterStack

A new (and better) implementation for multiple stacks. This stack manager saves fragment's class and it's current state (if the fragment implements StackInfo) instead of a complete fragment, which probably cause dangling references or exceptions, especially if you're handling nested fragments (like ViewPager usually does). It also reduces a fairly amount of memory used since it doesn't store entire fragments, with almost no noticeable recoil on CPU usage. So why don't you give it a try?
