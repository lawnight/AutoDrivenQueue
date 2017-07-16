1231231## AutoDrivenQueue
这是一个生产者和消费者的模型。消费者可以自驱动的处理数据。不用自己关心线程的创建和使用。曾用在游戏服务器中，处理大量客服端发上来的协议。

## 功能（feature）
- 消费者只需要定义怎么处理处理，而不用关心怎么去取这些数据。
- 底层会把生产者的数据合理的分配到线程池中，用你定义的方法来处理这些数据
- 支持多个生产者。
- 对于多个生产者可以轻易的控制是并行的消耗这些数据，还是串行队列的消耗。

## 怎么使用（How to use）

核心类就是AutoDrivenActionQueue。这个类基本实现了我们需要的功能。
首先我们需要新建一个AutoDrivenActionQueue，生成者只需要把产生的数据，通过特别的方式加入这个队列里。
```
  AutoDrivenActionQueue selfDrivenActionQueue = new AutoDrivenActionQueue();
```

假如生产方法如下
```
public void producer() {
	String words = "hello world!";
	CustomAction action = new CustomAction(words);
	selfDrivenActionQueue.add(action);
}
```
生成不断调用producer方法，产生很多words，customAction就是我们定义的消费者应该怎么处理这些数据。
将数据包装在customAction里面，丢给selfDrivenActionQueue。OK，it's done。底层会自动取出队列里面的数据执行。

所以你只需要把AutoDrivenActionQueue类引入你项目中，你就可以随意的使用这种框架了。
