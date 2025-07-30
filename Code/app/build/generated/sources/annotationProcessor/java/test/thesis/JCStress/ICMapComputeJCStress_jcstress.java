package thesis.JCStress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.ForkedTestConfig;
import org.openjdk.jcstress.infra.collectors.TestResult;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.WorkerSync;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.os.AffinitySupport;
import org.openjdk.jcstress.vm.AllocProfileSupport;
import org.openjdk.jcstress.infra.runners.ResourceEstimator;
import org.openjdk.jcstress.infra.runners.VoidThread;
import org.openjdk.jcstress.infra.runners.LongThread;
import org.openjdk.jcstress.infra.runners.CounterThread;
import thesis.JCStress.ICMapComputeJCStress;
import org.openjdk.jcstress.infra.results.IZ_Result;

public final class ICMapComputeJCStress_jcstress extends Runner<IZ_Result> {

    volatile WorkerSync workerSync;

    public ICMapComputeJCStress_jcstress(ForkedTestConfig config) {
        super(config);
    }

    @Override
    public void sanityCheck(Counter<IZ_Result> counter) throws Throwable {
        jcstress_sanityCheck_API(counter);
        jcstress_sanityCheck_Resource(counter);
    }

    private static class JcstressThread_APICheck_actor1 extends VoidThread {
        ICMapComputeJCStress t;
        ICMapComputeJCStress s;
        IZ_Result r;

        public JcstressThread_APICheck_actor1(ICMapComputeJCStress t, ICMapComputeJCStress s, IZ_Result r) {
            super("JcstressThread_APICheck_actor1");
            this.t = t;
            this.s = s;
            this.r = r;
        }

        public void internalRun() {
            s.actor1();
        };

        public void purge() {
            t = null;
            s = null;
            r = null;
        }
    }

    private static class JcstressThread_APICheck_actor2 extends VoidThread {
        ICMapComputeJCStress t;
        ICMapComputeJCStress s;
        IZ_Result r;

        public JcstressThread_APICheck_actor2(ICMapComputeJCStress t, ICMapComputeJCStress s, IZ_Result r) {
            super("JcstressThread_APICheck_actor2");
            this.t = t;
            this.s = s;
            this.r = r;
        }

        public void internalRun() {
            s.actor2();
        };

        public void purge() {
            t = null;
            s = null;
            r = null;
        }
    }

    private void jcstress_sanityCheck_API(Counter<IZ_Result> counter) throws Throwable {
        final ICMapComputeJCStress s = new ICMapComputeJCStress();
        final IZ_Result r = new IZ_Result();
        VoidThread a0 = new JcstressThread_APICheck_actor1(null, s, r);
        VoidThread a1 = new JcstressThread_APICheck_actor2(null, s, r);
        a0.start();
        a1.start();
        a0.join();
        if (a0.throwable() != null) {
            throw a0.throwable();
        }
        a0.purge();
        a1.join();
        if (a1.throwable() != null) {
            throw a1.throwable();
        }
        a1.purge();
            s.arbiter(r);
        counter.record(r, 1);
    }

    private static class JcstressThread_ResourceCheck_actor1 extends LongThread {
        ICMapComputeJCStress[] ss;
        IZ_Result[] rs;
        int size;

        public JcstressThread_ResourceCheck_actor1(ICMapComputeJCStress[] ss, IZ_Result[] rs, int size) {
            super("JcstressThread_ResourceCheck_actor1");
            this.ss = ss;
            this.rs = rs;
            this.size = size;
        }

        public long internalRun() {
            long a1 = AllocProfileSupport.getAllocatedBytes();
            jcstress_check_actor1(ss, rs, size);
            long a2 = AllocProfileSupport.getAllocatedBytes();
            return a2 - a1;
        }

        private void jcstress_check_actor1(ICMapComputeJCStress[] ls, IZ_Result[] lr, int size) {
            for (int c = 0; c < size; c++) {
                ls[c].actor1();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
        }
    }

    private static class JcstressThread_ResourceCheck_actor2 extends LongThread {
        ICMapComputeJCStress[] ss;
        IZ_Result[] rs;
        int size;

        public JcstressThread_ResourceCheck_actor2(ICMapComputeJCStress[] ss, IZ_Result[] rs, int size) {
            super("JcstressThread_ResourceCheck_actor2");
            this.ss = ss;
            this.rs = rs;
            this.size = size;
        }

        public long internalRun() {
            long a1 = AllocProfileSupport.getAllocatedBytes();
            jcstress_check_actor2(ss, rs, size);
            long a2 = AllocProfileSupport.getAllocatedBytes();
            return a2 - a1;
        }

        private void jcstress_check_actor2(ICMapComputeJCStress[] ls, IZ_Result[] lr, int size) {
            for (int c = 0; c < size; c++) {
                ls[c].actor2();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
        }
    }

    private static class TestResourceEstimator implements ResourceEstimator {
        final Counter<IZ_Result> counter;

        public TestResourceEstimator(Counter<IZ_Result> counter) {
            this.counter = counter;
        }

        public void runWith(int size, long[] cnts) {
            long time1 = System.nanoTime();
            long alloc1 = AllocProfileSupport.getAllocatedBytes();
            ICMapComputeJCStress[] ls = new ICMapComputeJCStress[size];
            IZ_Result[] lr = new IZ_Result[size];
            for (int c = 0; c < size; c++) {
                ICMapComputeJCStress s = new ICMapComputeJCStress();
                IZ_Result r = new IZ_Result();
                lr[c] = r;
                ls[c] = s;
            }
            LongThread a0 = new JcstressThread_ResourceCheck_actor1(ls, lr, size);
            LongThread a1 = new JcstressThread_ResourceCheck_actor2(ls, lr, size);
            a0.start();
            a1.start();
            try {
                a0.join();
                cnts[0] += a0.result();
                a0.purge();
            } catch (InterruptedException e) {
            }
            try {
                a1.join();
                cnts[0] += a1.result();
                a1.purge();
            } catch (InterruptedException e) {
            }
            for (int c = 0; c < size; c++) {
                ls[c].arbiter(lr[c]);
            }
            for (int c = 0; c < size; c++) {
                counter.record(lr[c], 1);
            }
            long time2 = System.nanoTime();
            long alloc2 = AllocProfileSupport.getAllocatedBytes();
            cnts[0] += alloc2 - alloc1;
            cnts[1] += time2 - time1;
        }
    }

    private void jcstress_sanityCheck_Resource(Counter<IZ_Result> counter) throws Throwable {
        config.adjustStrideCount(new TestResourceEstimator(counter));
    }

    @Override
    public ArrayList<CounterThread<IZ_Result>> internalRun() {
        int len = config.strideSize * config.strideCount;
        ICMapComputeJCStress[] ls = new ICMapComputeJCStress[len];
        IZ_Result[] lr = new IZ_Result[len];
        for (int c = 0; c < len; c++) {
            ls[c] = new ICMapComputeJCStress();
            lr[c] = new IZ_Result();
        }
        workerSync = new WorkerSync(false, 2, config.spinLoopStyle);

        control.stopping = false;

        if (config.localAffinity) {
            try {
                AffinitySupport.tryBind();
            } catch (Exception e) {
                // Do not care
            }
        }

        ArrayList<CounterThread<IZ_Result>> threads = new ArrayList<>(2);
        threads.add(new JcstressThread_actor1(ls, lr, null));
        threads.add(new JcstressThread_actor2(ls, lr, null));

        for (CounterThread<IZ_Result> t : threads) {
            t.start();
        }

        if (config.time > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(config.time);
            } catch (InterruptedException e) {
            }
        }

        control.stopping = true;

        return threads;
    }

    public static void jcstress_ni_consume_final(Counter<IZ_Result> cnt, ICMapComputeJCStress[] ls, IZ_Result[] lr, ICMapComputeJCStress test, int len, int a) {
        int left = a * len / 2;
        int right = (a + 1) * len / 2;
        for (int c = left; c < right; c++) {
            IZ_Result r = lr[c];
            ICMapComputeJCStress s = ls[c];
            s.arbiter(r);
            cnt.record(r, 1);
        }
    }

    public static void jcstress_consume_reinit(Counter<IZ_Result> cnt, ICMapComputeJCStress[] ls, IZ_Result[] lr, ICMapComputeJCStress test, int len, int a) {
        int left = a * len / 2;
        int right = (a + 1) * len / 2;
        for (int c = left; c < right; c++) {
            IZ_Result r = lr[c];
            ICMapComputeJCStress s = ls[c];
            s.arbiter(r);
            ls[c] = new ICMapComputeJCStress();
            cnt.record(r, 1);
            r.r1 = 0;
            r.r2 = false;
        }
    }

    public class JcstressThread_actor1 extends CounterThread<IZ_Result> {
        ICMapComputeJCStress[] ss;
        IZ_Result[] rs;
        ICMapComputeJCStress test;

        public JcstressThread_actor1(ICMapComputeJCStress[] ss, IZ_Result[] rs, ICMapComputeJCStress test) {
            super("JcstressThread_actor1");
            this.ss = ss;
            this.rs = rs;
            this.test = test;
        }

        public Counter<IZ_Result> internalRun() {
            return jcstress_iteration_actor1();
        }

        private Counter<IZ_Result> jcstress_iteration_actor1() {
            int len = config.strideSize * config.strideCount;
            int stride = config.strideSize;
            Counter<IZ_Result> counter = new Counter<>();
            if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[0]);
            while (true) {
                WorkerSync sync = workerSync;
                int check = 0;
                for (int start = 0; start < len; start += stride) {
                    jcstress_stride_actor1(start, start + stride);
                    check += 2;
                    sync.awaitCheckpoint(check);
                }
                if (sync.stopping) {
                    jcstress_ni_consume_final(counter, ss, rs, null, len, 0);
                    return counter;
                } else {
                    jcstress_consume_reinit(counter, ss, rs, null, len, 0);
                }
                if (sync.tryStartUpdate()) {
                    workerSync = new WorkerSync(control.stopping, 2, config.spinLoopStyle);
                }
                sync.postUpdate();
            }
        }

        private void jcstress_stride_actor1(int start, int end) {
            ICMapComputeJCStress[] ls = ss;
            IZ_Result[] lr = rs;
            for (int c = start; c < end; c++) {
                ICMapComputeJCStress s = ls[c];
                s.actor1();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
            test = null;
        }
    }

    public class JcstressThread_actor2 extends CounterThread<IZ_Result> {
        ICMapComputeJCStress[] ss;
        IZ_Result[] rs;
        ICMapComputeJCStress test;

        public JcstressThread_actor2(ICMapComputeJCStress[] ss, IZ_Result[] rs, ICMapComputeJCStress test) {
            super("JcstressThread_actor2");
            this.ss = ss;
            this.rs = rs;
            this.test = test;
        }

        public Counter<IZ_Result> internalRun() {
            return jcstress_iteration_actor2();
        }

        private Counter<IZ_Result> jcstress_iteration_actor2() {
            int len = config.strideSize * config.strideCount;
            int stride = config.strideSize;
            Counter<IZ_Result> counter = new Counter<>();
            if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[1]);
            while (true) {
                WorkerSync sync = workerSync;
                int check = 0;
                for (int start = 0; start < len; start += stride) {
                    jcstress_stride_actor2(start, start + stride);
                    check += 2;
                    sync.awaitCheckpoint(check);
                }
                if (sync.stopping) {
                    jcstress_ni_consume_final(counter, ss, rs, null, len, 1);
                    return counter;
                } else {
                    jcstress_consume_reinit(counter, ss, rs, null, len, 1);
                }
                if (sync.tryStartUpdate()) {
                    workerSync = new WorkerSync(control.stopping, 2, config.spinLoopStyle);
                }
                sync.postUpdate();
            }
        }

        private void jcstress_stride_actor2(int start, int end) {
            ICMapComputeJCStress[] ls = ss;
            IZ_Result[] lr = rs;
            for (int c = start; c < end; c++) {
                ICMapComputeJCStress s = ls[c];
                s.actor2();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
            test = null;
        }
    }

}
