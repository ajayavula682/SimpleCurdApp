# Kubernetes Deployment Guide

This guide explains the Kubernetes resources used to deploy the `SimpleCurdApp` and provides the necessary commands to manage the deployment.

## Resource Files

### 1. `deployment.yaml`
This file defines the **Deployment** of the application.
- **Kind:** Deployment
- **Replicas:** 2 (Initial number of pod instances)
- **Image:** `simplecurdapp:slim` (The Docker image built locally)
- **Ports:** Exposes container port `8082`.
- **Resources:** Sets CPU and Memory requests/limits, which are essential for the Horizontal Pod Autoscaler to function.

### 2. `service.yaml`
This file defines the **Service** that exposes the application.
- **Kind:** Service
- **Type:** LoadBalancer (Exposes the service externally)
- **Port:** Maps external port `8088` to internal container port `8082`.
- **Selector:** routes traffic to pods labeled `app: simplecurdapp`.

### 3. `hpa.yaml`
This file defines the **Horizontal Pod Autoscaler (HPA)**.
- **Kind:** HorizontalPodAutoscaler
- **Scale Target:** The `simplecurdapp` Deployment.
- **Range:** Scales between **2** (min) and **10** (max) replicas.
- **Trigger:** Scales up when average CPU utilization exceeds **50%**.

---

## Deployment Commands

### Step 1: Apply the Configuration
Run the following commands to create or update the resources in your cluster:

```bash
# 1. Deploy the application
kubectl apply -f deployment.yaml

# 2. Create the service
kubectl apply -f service.yaml

# 3. specific the autoscaler
kubectl apply -f hpa.yaml
```

Alternatively, you can apply all files in the current directory:
```bash
kubectl apply -f .
```

### Step 2: Verification
Check the status of your deployment, pods, and services:

**Check running Pods:**
```bash
kubectl get pods
```

**Check the HPA status:**
```bash
kubectl get hpa
```

**Check the Service (and get external IP if applicable):**
```bash
kubectl get svc
```

**Watch the pods in real-time:**
```bash
kubectl get pods -w
```

## Troubleshooting
If a pod is crashing or in an error state, check its logs:
```bash
kubectl logs <pod-name>
```

If the HPA is not scaling or shows "unknown", ensure your cluster has a metrics server installed:
```bash
kubectl top nodes
kubectl top pods
```
