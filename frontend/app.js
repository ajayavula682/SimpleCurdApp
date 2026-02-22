// API Base URL - Update this to match your backend
const API_BASE_URL = 'http://localhost:8082/api';

// State
let products = [];
let users = [];
let categories = [];

// Tab Management
document.querySelectorAll('.tab-btn').forEach(button => {
    button.addEventListener('click', () => {
        const tabName = button.dataset.tab;
        
        // Update active tab button
        document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
        
        // Update active tab content
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
        document.getElementById(tabName).classList.add('active');
        
        // Load data for the selected tab
        if (tabName === 'products' && products.length === 0) {
            loadProducts();
            loadCategories();
        } else if (tabName === 'users' && users.length === 0) {
            loadUsers();
        } else if (tabName === 'api-info') {
            loadApiInfo();
        }
    });
});

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    loadCategories();
});

// ============ PRODUCTS ============

async function loadProducts() {
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        if (!response.ok) throw new Error('Failed to load products');
        
        products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error('Error loading products:', error);
        showToast('Failed to load products', 'error');
        document.getElementById('productsTableBody').innerHTML = 
            '<tr><td colspan="7" class="loading">Error loading products</td></tr>';
    }
}

async function loadCategories() {
    try {
        const response = await fetch(`${API_BASE_URL}/products/categories`);
        if (!response.ok) return;
        
        categories = await response.json();
        const categoryFilter = document.getElementById('categoryFilter');
        categoryFilter.innerHTML = '<option value="">All Categories</option>';
        categories.forEach(category => {
            categoryFilter.innerHTML += `<option value="${category}">${category}</option>`;
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

function displayProducts(productList) {
    const tbody = document.getElementById('productsTableBody');
    
    if (productList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="loading">No products found</td></tr>';
        return;
    }
    
    tbody.innerHTML = productList.map(product => `
        <tr>
            <td>${product.id}</td>
            <td><strong>${product.name}</strong></td>
            <td>${product.category}</td>
            <td>$${parseFloat(product.price).toFixed(2)}</td>
            <td>${product.quantity}</td>
            <td>
                <span class="status-badge ${product.isAvailable ? 'status-available' : 'status-unavailable'}">
                    ${product.isAvailable ? 'Available' : 'Unavailable'}
                </span>
            </td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-warning" onclick="editProduct(${product.id})">Edit</button>
                    <button class="btn btn-danger" onclick="deleteProduct(${product.id})">Delete</button>
                </div>
            </td>
        </tr>
    `).join('');
}

async function filterProducts() {
    const searchTerm = document.getElementById('productSearch').value.toLowerCase();
    const category = document.getElementById('categoryFilter').value;
    const availability = document.getElementById('availabilityFilter').value;
    
    let filtered = [...products];
    
    if (searchTerm) {
        filtered = filtered.filter(p => 
            p.name.toLowerCase().includes(searchTerm) || 
            p.description?.toLowerCase().includes(searchTerm)
        );
    }
    
    if (category) {
        filtered = filtered.filter(p => p.category === category);
    }
    
    if (availability === 'available') {
        filtered = filtered.filter(p => p.isAvailable);
    } else if (availability === 'unavailable') {
        filtered = filtered.filter(p => !p.isAvailable);
    }
    
    displayProducts(filtered);
}

function openProductModal(productId = null) {
    const modal = document.getElementById('productModal');
    const title = document.getElementById('productModalTitle');
    const form = document.getElementById('productForm');
    
    if (productId) {
        const product = products.find(p => p.id === productId);
        title.textContent = 'Edit Product';
        document.getElementById('productId').value = product.id;
        document.getElementById('productName').value = product.name;
        document.getElementById('productCategory').value = product.category;
        document.getElementById('productDescription').value = product.description || '';
        document.getElementById('productPrice').value = product.price;
        document.getElementById('productQuantity').value = product.quantity;
        document.getElementById('productAvailable').checked = product.isAvailable;
    } else {
        title.textContent = 'Add Product';
        form.reset();
        document.getElementById('productId').value = '';
    }
    
    modal.classList.add('show');
}

function closeProductModal() {
    document.getElementById('productModal').classList.remove('show');
}

async function saveProduct(event) {
    event.preventDefault();
    
    const productId = document.getElementById('productId').value;
    const productData = {
        name: document.getElementById('productName').value,
        category: document.getElementById('productCategory').value,
        description: document.getElementById('productDescription').value,
        price: parseFloat(document.getElementById('productPrice').value),
        quantity: parseInt(document.getElementById('productQuantity').value),
        isAvailable: document.getElementById('productAvailable').checked
    };
    
    // Validation
    if (!productData.name || !productData.category || !productData.price || productData.quantity === null) {
        showToast('Please fill in all required fields', 'error');
        return;
    }
    
    if (isNaN(productData.price) || productData.price <= 0) {
        showToast('Please enter a valid price', 'error');
        return;
    }
    
    if (isNaN(productData.quantity) || productData.quantity < 0) {
        showToast('Please enter a valid quantity', 'error');
        return;
    }
    
    try {
        let response;
        if (productId) {
            response = await fetch(`${API_BASE_URL}/products/${productId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(productData)
            });
        } else {
            response = await fetch(`${API_BASE_URL}/products`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(productData)
            });
        }
        
        if (!response.ok) {
            // Get detailed error message from backend
            const errorData = await response.json().catch(() => ({}));
            const errorMessage = errorData.message || errorData.error || `Server returned ${response.status}`;
            console.error('Server error details:', errorData);
            throw new Error(errorMessage);
        }
        
        showToast(`Product ${productId ? 'updated' : 'created'} successfully`, 'success');
        closeProductModal();
        loadProducts();
        loadCategories();
    } catch (error) {
        console.error('Error saving product:', error);
        console.error('Product data sent:', productData);
        showToast(`Failed to save product: ${error.message}`, 'error');
    }
}

async function editProduct(id) {
    openProductModal(id);
}

async function deleteProduct(id) {
    if (!confirm('Are you sure you want to delete this product?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/products/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Failed to delete product');
        
        showToast('Product deleted successfully', 'success');
        loadProducts();
        loadCategories();
    } catch (error) {
        console.error('Error deleting product:', error);
        showToast('Failed to delete product', 'error');
    }
}

// ============ USERS ============

async function loadUsers() {
    try {
        const response = await fetch(`${API_BASE_URL}/users`);
        if (!response.ok) throw new Error('Failed to load users');
        
        users = await response.json();
        displayUsers(users);
    } catch (error) {
        console.error('Error loading users:', error);
        showToast('Failed to load users', 'error');
        document.getElementById('usersTableBody').innerHTML = 
            '<tr><td colspan="7" class="loading">Error loading users</td></tr>';
    }
}

function displayUsers(userList) {
    const tbody = document.getElementById('usersTableBody');
    
    if (userList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="loading">No users found</td></tr>';
        return;
    }
    
    tbody.innerHTML = userList.map(user => `
        <tr>
            <td>${user.id}</td>
            <td><strong>${user.name}</strong></td>
            <td>${user.email}</td>
            <td>${user.phone || 'N/A'}</td>
            <td>${user.address || 'N/A'}</td>
            <td>
                <span class="status-badge ${user.isActive ? 'status-active' : 'status-inactive'}">
                    ${user.isActive ? 'Active' : 'Inactive'}
                </span>
            </td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-warning" onclick="editUser(${user.id})">Edit</button>
                    <button class="btn ${user.isActive ? 'btn-secondary' : 'btn-success'}" 
                            onclick="toggleUserStatus(${user.id}, ${user.isActive})">
                        ${user.isActive ? 'Deactivate' : 'Activate'}
                    </button>
                    <button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button>
                </div>
            </td>
        </tr>
    `).join('');
}

async function filterUsers() {
    const searchTerm = document.getElementById('userSearch').value.toLowerCase();
    const status = document.getElementById('statusFilter').value;
    
    let filtered = [...users];
    
    if (searchTerm) {
        filtered = filtered.filter(u => 
            u.name.toLowerCase().includes(searchTerm) || 
            u.email.toLowerCase().includes(searchTerm)
        );
    }
    
    if (status === 'active') {
        filtered = filtered.filter(u => u.isActive);
    } else if (status === 'inactive') {
        filtered = filtered.filter(u => !u.isActive);
    }
    
    displayUsers(filtered);
}

function openUserModal(userId = null) {
    const modal = document.getElementById('userModal');
    const title = document.getElementById('userModalTitle');
    const form = document.getElementById('userForm');
    
    if (userId) {
        const user = users.find(u => u.id === userId);
        title.textContent = 'Edit User';
        document.getElementById('userId').value = user.id;
        document.getElementById('userName').value = user.name;
        document.getElementById('userEmail').value = user.email;
        document.getElementById('userPhone').value = user.phone || '';
        document.getElementById('userAddress').value = user.address || '';
        document.getElementById('userActive').checked = user.isActive;
    } else {
        title.textContent = 'Add User';
        form.reset();
        document.getElementById('userId').value = '';
    }
    
    modal.classList.add('show');
}

function closeUserModal() {
    document.getElementById('userModal').classList.remove('show');
}

async function saveUser(event) {
    event.preventDefault();
    
    const userId = document.getElementById('userId').value;
    const userData = {
        name: document.getElementById('userName').value,
        email: document.getElementById('userEmail').value,
        phone: document.getElementById('userPhone').value,
        address: document.getElementById('userAddress').value,
        isActive: document.getElementById('userActive').checked
    };
    
    try {
        let response;
        if (userId) {
            response = await fetch(`${API_BASE_URL}/users/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
        } else {
            response = await fetch(`${API_BASE_URL}/users`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
        }
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to save user');
        }
        
        showToast(`User ${userId ? 'updated' : 'created'} successfully`, 'success');
        closeUserModal();
        loadUsers();
    } catch (error) {
        console.error('Error saving user:', error);
        showToast(error.message || 'Failed to save user', 'error');
    }
}

async function editUser(id) {
    openUserModal(id);
}

async function toggleUserStatus(id, isActive) {
    try {
        const endpoint = isActive ? 'deactivate' : 'activate';
        const response = await fetch(`${API_BASE_URL}/users/${id}/${endpoint}`, {
            method: 'PATCH'
        });
        
        if (!response.ok) throw new Error('Failed to update user status');
        
        showToast(`User ${isActive ? 'deactivated' : 'activated'} successfully`, 'success');
        loadUsers();
    } catch (error) {
        console.error('Error updating user status:', error);
        showToast('Failed to update user status', 'error');
    }
}

async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Failed to delete user');
        
        showToast('User deleted successfully', 'success');
        loadUsers();
    } catch (error) {
        console.error('Error deleting user:', error);
        showToast('Failed to delete user', 'error');
    }
}

// ============ API INFO ============

async function loadApiInfo() {
    try {
        // Load app info
        const infoResponse = await fetch(`${API_BASE_URL}/info`);
        if (infoResponse.ok) {
            const info = await infoResponse.json();
            document.getElementById('appInfo').innerHTML = `
                <p><strong>Application:</strong> ${info.application}</p>
                <p><strong>Version:</strong> ${info.version}</p>
                <p><strong>Description:</strong> ${info.description}</p>
                <p><strong>Timestamp:</strong> ${new Date(info.timestamp).toLocaleString()}</p>
            `;
        }
        
        // Load health status
        const healthResponse = await fetch(`${API_BASE_URL}/health`);
        if (healthResponse.ok) {
            const health = await healthResponse.json();
            const statusClass = health.status === 'UP' ? 'status-active' : 'status-inactive';
            document.getElementById('healthInfo').innerHTML = `
                <p><strong>Status:</strong> <span class="status-badge ${statusClass}">${health.status}</span></p>
                <p><strong>Last Check:</strong> ${new Date(health.timestamp).toLocaleString()}</p>
            `;
        }
    } catch (error) {
        console.error('Error loading API info:', error);
        document.getElementById('appInfo').innerHTML = '<p class="loading">Failed to load API information</p>';
        document.getElementById('healthInfo').innerHTML = '<p class="loading">Failed to load health status</p>';
    }
}

// ============ UTILITIES ============

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Close modals when clicking outside
window.onclick = function(event) {
    const productModal = document.getElementById('productModal');
    const userModal = document.getElementById('userModal');
    
    if (event.target === productModal) {
        closeProductModal();
    }
    if (event.target === userModal) {
        closeUserModal();
    }
};
