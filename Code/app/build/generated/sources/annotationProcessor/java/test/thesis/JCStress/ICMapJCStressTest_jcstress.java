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
import thesis.JCStress.ICMapJCStressTest;
import org.openjdk.jcstress.infra.results.Z_Result;

public final class ICMapJCStressTest_jcstress extends Runner<Z_Result> {

    volatile WorkerSync workerSync;

    public ICMapJCStressTest_jcstress(ForkedTestConfig config) {
        super(config);
    }

    @Override
    public void sanityCheck(Counter<Z_Result> counter) throws Throwable {
        jcstress_sanityCheck_API(counter);
        jcstress_sanityCheck_Resource(counter);
    }

    private static class JcstressThread_APICheck_actor1 extends VoidThread {
        ICMapJCStressTest t;
        ICMapJCStressTest s;
        Z_Result r;

        public JcstressThread_APICheck_actor1(ICMapJCStressTest t, ICMapJCStressTest s, Z_Result r) {
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
        ICMapJCStressTest t;
        ICMapJCStressTest s;
        Z_Result r;

        public JcstressThread_APICheck_actor2(ICMapJCStressTest t, ICMapJCStressTest s, Z_Result r) {
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

    private static class JcstressThread_APICheck_actor3 extends VoidThread {
        ICMapJCStressTest t;
        ICMapJCStressTest s;
        Z_Result r;

        public JcstressThread_APICheck_actor3(ICMapJCStressTest t, ICMapJCStressTest s, Z_Result r) {
            super("JcstressThread_APICheck_actor3");
            this.t = t;
            this.s = s;
            this.r = r;
        }

        public void internalRun() {
            s.actor3();
        };

        public void purge() {
            t = null;
            s = null;
            r = null;
        }
    }

    private void jcstress_sanityCheck_API(Counter<Z_Result> counter) throws Throwable {
        final ICMapJCStressTest s = new ICMapJCStressTest();
        final Z_Result r = new Z_Result();
        VoidThread a0 = new JcstressThread_APICheck_actor1(null, s, r);
        VoidThread a1 = new JcstressThread_APICheck_actor2(null, s, r);
        VoidThread a2 = new JcstressThread_APICheck_actor3(null, s, r);
        a0.start();
        a1.start();
        a2.start();
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
        a2.join();
        if (a2.throwable() != null) {
            throw a2.throwable();
        }
        a2.purge();
            s.arbiter(r);
        counter.record(r, 1);
    }

    private static class JcstressThread_ResourceCheck_actor1 extends LongThread {
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        int size;

        public JcstressThread_ResourceCheck_actor1(ICMapJCStressTest[] ss, Z_Result[] rs, int size) {
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

        private void jcstress_check_actor1(ICMapJCStressTest[] ls, Z_Result[] lr, int size) {
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
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        int size;

        public JcstressThread_ResourceCheck_actor2(ICMapJCStressTest[] ss, Z_Result[] rs, int size) {
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

        private void jcstress_check_actor2(ICMapJCStressTest[] ls, Z_Result[] lr, int size) {
            for (int c = 0; c < size; c++) {
                ls[c].actor2();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
        }
    }

    private static class JcstressThread_ResourceCheck_actor3 extends LongThread {
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        int size;

        public JcstressThread_ResourceCheck_actor3(ICMapJCStressTest[] ss, Z_Result[] rs, int size) {
            super("JcstressThread_ResourceCheck_actor3");
            this.ss = ss;
            this.rs = rs;
            this.size = size;
        }

        public long internalRun() {
            long a1 = AllocProfileSupport.getAllocatedBytes();
            jcstress_check_actor3(ss, rs, size);
            long a2 = AllocProfileSupport.getAllocatedBytes();
            return a2 - a1;
        }

        private void jcstress_check_actor3(ICMapJCStressTest[] ls, Z_Result[] lr, int size) {
            for (int c = 0; c < size; c++) {
                ls[c].actor3();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
        }
    }

    private static class TestResourceEstimator implements ResourceEstimator {
        final Counter<Z_Result> counter;

        public TestResourceEstimator(Counter<Z_Result> counter) {
            this.counter = counter;
        }

        public void runWith(int size, long[] cnts) {
            long time1 = System.nanoTime();
            long alloc1 = AllocProfileSupport.getAllocatedBytes();
            ICMapJCStressTest[] ls = new ICMapJCStressTest[size];
            Z_Result[] lr = new Z_Result[size];
            for (int c = 0; c < size; c++) {
                ICMapJCStressTest s = new ICMapJCStressTest();
                Z_Result r = new Z_Result();
                lr[c] = r;
                ls[c] = s;
            }
            LongThread a0 = new JcstressThread_ResourceCheck_actor1(ls, lr, size);
            LongThread a1 = new JcstressThread_ResourceCheck_actor2(ls, lr, size);
            LongThread a2 = new JcstressThread_ResourceCheck_actor3(ls, lr, size);
            a0.start();
            a1.start();
            a2.start();
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
            try {
                a2.join();
                cnts[0] += a2.result();
                a2.purge();
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

    private void jcstress_sanityCheck_Resource(Counter<Z_Result> counter) throws Throwable {
        config.adjustStrideCount(new TestResourceEstimator(counter));
    }

    @Override
    public ArrayList<CounterThread<Z_Result>> internalRun() {
        int len = config.strideSize * config.strideCount;
        ICMapJCStressTest[] ls = new ICMapJCStressTest[len];
        Z_Result[] lr = new Z_Result[len];
        for (int c = 0; c < len; c++) {
            ls[c] = new ICMapJCStressTest();
            lr[c] = new Z_Result();
        }
        workerSync = new WorkerSync(false, 3, config.spinLoopStyle);

        control.stopping = false;

        if (config.localAffinity) {
            try {
                AffinitySupport.tryBind();
            } catch (Exception e) {
                // Do not care
            }
        }

        ArrayList<CounterThread<Z_Result>> threads = new ArrayList<>(3);
        threads.add(new JcstressThread_actor1(ls, lr, null));
        threads.add(new JcstressThread_actor2(ls, lr, null));
        threads.add(new JcstressThread_actor3(ls, lr, null));

        for (CounterThread<Z_Result> t : threads) {
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

    public static void jcstress_ni_consume_final(Counter<Z_Result> cnt, ICMapJCStressTest[] ls, Z_Result[] lr, ICMapJCStressTest test, int len, int a) {
        int left = a * len / 3;
        int right = (a + 1) * len / 3;
        for (int c = left; c < right; c++) {
            Z_Result r = lr[c];
            ICMapJCStressTest s = ls[c];
            s.arbiter(r);
            cnt.record(r, 1);
        }
    }

    public static void jcstress_consume_reinit(Counter<Z_Result> cnt, ICMapJCStressTest[] ls, Z_Result[] lr, ICMapJCStressTest test, int len, int a) {
        int left = a * len / 3;
        int right = (a + 1) * len / 3;
        for (int c = left; c < right; c++) {
            Z_Result r = lr[c];
            ICMapJCStressTest s = ls[c];
            s.arbiter(r);
            ls[c] = new ICMapJCStressTest();
            cnt.record(r, 1);
            r.r1 = false;
        }
    }

    public class JcstressThread_actor1 extends CounterThread<Z_Result> {
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        ICMapJCStressTest test;

        public JcstressThread_actor1(ICMapJCStressTest[] ss, Z_Result[] rs, ICMapJCStressTest test) {
            super("JcstressThread_actor1");
            this.ss = ss;
            this.rs = rs;
            this.test = test;
        }

        public Counter<Z_Result> internalRun() {
            return jcstress_iteration_actor1();
        }

        private Counter<Z_Result> jcstress_iteration_actor1() {
            int len = config.strideSize * config.strideCount;
            int stride = config.strideSize;
            Counter<Z_Result> counter = new Counter<>();
            if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[0]);
            while (true) {
                WorkerSync sync = workerSync;
                int check = 0;
                for (int start = 0; start < len; start += stride) {
                    jcstress_stride_actor1(start, start + stride);
                    check += 3;
                    sync.awaitCheckpoint(check);
                }
                if (sync.stopping) {
                    jcstress_ni_consume_final(counter, ss, rs, null, len, 0);
                    return counter;
                } else {
                    jcstress_consume_reinit(counter, ss, rs, null, len, 0);
                }
                if (sync.tryStartUpdate()) {
                    workerSync = new WorkerSync(control.stopping, 3, config.spinLoopStyle);
                }
                sync.postUpdate();
            }
        }

        private void jcstress_stride_actor1(int start, int end) {
            ICMapJCStressTest[] ls = ss;
            Z_Result[] lr = rs;
            for (int c = start; c < end; c++) {
                ICMapJCStressTest s = ls[c];
                s.actor1();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
            test = null;
        }
    }

    public class JcstressThread_actor2 extends CounterThread<Z_Result> {
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        ICMapJCStressTest test;

        public JcstressThread_actor2(ICMapJCStressTest[] ss, Z_Result[] rs, ICMapJCStressTest test) {
            super("JcstressThread_actor2");
            this.ss = ss;
            this.rs = rs;
            this.test = test;
        }

        public Counter<Z_Result> internalRun() {
            return jcstress_iteration_actor2();
        }

        private Counter<Z_Result> jcstress_iteration_actor2() {
            int len = config.strideSize * config.strideCount;
            int stride = config.strideSize;
            Counter<Z_Result> counter = new Counter<>();
            if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[1]);
            while (true) {
                WorkerSync sync = workerSync;
                int check = 0;
                for (int start = 0; start < len; start += stride) {
                    jcstress_stride_actor2(start, start + stride);
                    check += 3;
                    sync.awaitCheckpoint(check);
                }
                if (sync.stopping) {
                    jcstress_ni_consume_final(counter, ss, rs, null, len, 1);
                    return counter;
                } else {
                    jcstress_consume_reinit(counter, ss, rs, null, len, 1);
                }
                if (sync.tryStartUpdate()) {
                    workerSync = new WorkerSync(control.stopping, 3, config.spinLoopStyle);
                }
                sync.postUpdate();
            }
        }

        private void jcstress_stride_actor2(int start, int end) {
            ICMapJCStressTest[] ls = ss;
            Z_Result[] lr = rs;
            for (int c = start; c < end; c++) {
                ICMapJCStressTest s = ls[c];
                s.actor2();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
            test = null;
        }
    }

    public class JcstressThread_actor3 extends CounterThread<Z_Result> {
        ICMapJCStressTest[] ss;
        Z_Result[] rs;
        ICMapJCStressTest test;

        public JcstressThread_actor3(ICMapJCStressTest[] ss, Z_Result[] rs, ICMapJCStressTest test) {
            super("JcstressThread_actor3");
            this.ss = ss;
            this.rs = rs;
            this.test = test;
        }

        public Counter<Z_Result> internalRun() {
            return jcstress_iteration_actor3();
        }

        private Counter<Z_Result> jcstress_iteration_actor3() {
            int len = config.strideSize * config.strideCount;
            int stride = config.strideSize;
            Counter<Z_Result> counter = new Counter<>();
            if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[2]);
            while (true) {
                WorkerSync sync = workerSync;
                int check = 0;
                for (int start = 0; start < len; start += stride) {
                    jcstress_stride_actor3(start, start + stride);
                    check += 3;
                    sync.awaitCheckpoint(check);
                }
                if (sync.stopping) {
                    jcstress_ni_consume_final(counter, ss, rs, null, len, 2);
                    return counter;
                } else {
                    jcstress_consume_reinit(counter, ss, rs, null, len, 2);
                }
                if (sync.tryStartUpdate()) {
                    workerSync = new WorkerSync(control.stopping, 3, config.spinLoopStyle);
                }
                sync.postUpdate();
            }
        }

        private void jcstress_stride_actor3(int start, int end) {
            ICMapJCStressTest[] ls = ss;
            Z_Result[] lr = rs;
            for (int c = start; c < end; c++) {
                ICMapJCStressTest s = ls[c];
                s.actor3();
            }
        }

        public void purge() {
            ss = null;
            rs = null;
            test = null;
        }
    }

}
