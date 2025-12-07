const { Button, Table, Tabs, Layout, Menu, Modal, Form, Input, Select, Tag, Card, Descriptions, Space, message, InputNumber, Drawer } = antd;
const { useState, useEffect } = React;
const { Header, Content } = Layout;
const { TabPane } = Tabs;
const { Column, Pie, Line, Bar } = Plots;

// --- Config Manager Component ---
const ConfigManager = () => {
    const [configs, setConfigs] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [form] = Form.useForm();
    const [resultModalVisible, setResultModalVisible] = useState(false);
    const [currentResult, setCurrentResult] = useState(null);

    const fetchConfigs = async () => {
        setLoading(true);
        try {
            const res = await axios.get('/api/configs');
            setConfigs(res.data);
        } catch (err) {
            message.error("Failed to load configs");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchConfigs();
    }, []);

    const handleAdd = async (values) => {
        try {
            await axios.post('/api/configs', {
                ...values,
                lastRunTime: 0
            });
            message.success("Config added");
            setIsModalVisible(false);
            form.resetFields();
            fetchConfigs();
        } catch (err) {
            message.error("Failed to add config");
        }
    };
    
    const handleDelete = async (id) => {
        try {
            await axios.delete(`/api/configs/${id}`);
            message.success("Config deleted");
            fetchConfigs();
        } catch (err) {
            message.error("Failed to delete config");
        }
    };
    
    const viewResult = async (id) => {
        try {
            const res = await axios.get(`/api/configs/${id}/result`);
            setCurrentResult(JSON.stringify(res.data, null, 2));
            setResultModalVisible(true);
        } catch (err) {
            message.error("Failed to fetch result");
        }
    };

    const columns = [
        { title: 'ID', dataIndex: 'id', key: 'id' },
        { title: 'Description', dataIndex: 'description', key: 'description' },
        { title: 'DSL', dataIndex: 'dsl', key: 'dsl', ellipsis: true },
        { title: 'Frequency (ms)', dataIndex: 'frequencyMs', key: 'frequencyMs' },
        { title: 'Analysis Type', dataIndex: 'analysisType', key: 'analysisType' },
        { 
            title: 'Action', 
            key: 'action',
            render: (_, record) => (
                <Space>
                    <Button onClick={() => viewResult(record.id)}>View Result</Button>
                    <Button danger onClick={() => handleDelete(record.id)}>Delete</Button>
                </Space>
            )
        }
    ];

    return (
        <div>
            <Button type="primary" onClick={() => setIsModalVisible(true)} style={{ marginBottom: 16 }}>
                Add New Metric
            </Button>
            <Table dataSource={configs} columns={columns} rowKey="id" loading={loading} />

            <Modal title="Add Metric Config" open={isModalVisible} onOk={() => form.submit()} onCancel={() => setIsModalVisible(false)}>
                <Form form={form} onFinish={handleAdd} layout="vertical">
                    <Form.Item name="id" label="ID" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="description" label="Description" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="dsl" label="DSL Script" rules={[{ required: true }]}>
                        <Input.TextArea rows={4} placeholder='METRIC ... ON ... AS ...' />
                    </Form.Item>
                    <Form.Item name="frequencyMs" label="Frequency (ms)" initialValue={30000}>
                        <InputNumber min={1000} style={{ width: '100%' }} />
                    </Form.Item>
                    <Form.Item name="analysisType" label="Analysis Type" initialValue="map">
                        <Select>
                            <Select.Option value="map">Map (Raw Data)</Select.Option>
                            <Select.Option value="trend">Trend (Time Series)</Select.Option>
                        </Select>
                    </Form.Item>
                </Form>
            </Modal>
            
            <Modal title="Execution Result" open={resultModalVisible} onCancel={() => setResultModalVisible(false)} footer={null} width={800}>
                <pre style={{ maxHeight: '600px', overflow: 'auto', background: '#f5f5f5', padding: '10px' }}>
                    {currentResult || "No result yet"}
                </pre>
            </Modal>
        </div>
    );
};

// --- Slow Job Components (From previous implementation) ---
const JobDetail = ({ appId, onBack }) => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        axios.get(`/api/job/${appId}/details`).then(res => {
            setData(res.data);
            setLoading(false);
        });
    }, [appId]);

    if (loading || !data) return <div>Loading...</div>;

    const { job, stages, tables } = data;

    const shuffleConfig = {
        data: stages,
        xField: 'name',
        yField: 'shuffle_read_bytes',
        color: '#1890ff',
        label: { position: 'middle', style: { fill: '#FFFFFF', opacity: 0.6 } },
        xAxis: { label: { autoHide: true, autoRotate: false } },
    };

    const tableColumns = [
        { title: 'Table Name', dataIndex: 'table_name' },
        { title: 'Type', dataIndex: 'type', render: t => <Tag color={t === 'INPUT' ? 'blue' : 'green'}>{t}</Tag> },
        { title: 'Size', dataIndex: 'space_bytes', render: v => (v / 1024 / 1024).toFixed(2) + ' MB' },
        { title: 'Files', dataIndex: 'file_count' },
        { title: 'Format', dataIndex: 'format' },
    ];

    return (
        <div>
            <Button onClick={onBack} style={{ marginBottom: 16 }}>Back to List</Button>
            <Descriptions title="Job Overview" bordered>
                <Descriptions.Item label="App ID">{job.app_id}</Descriptions.Item>
                <Descriptions.Item label="Name">{job.name}</Descriptions.Item>
                <Descriptions.Item label="Duration">{(job.duration / 60000).toFixed(1)} min</Descriptions.Item>
                <Descriptions.Item label="Slow Reason"><Tag color="red">{job.slow_reason || 'N/A'}</Tag></Descriptions.Item>
            </Descriptions>
            
            <Tabs defaultActiveKey="1" style={{ marginTop: 20 }}>
                <TabPane tab="Shuffle Analysis" key="1">
                    <Card title="Shuffle Read per Stage">
                        <Column {...shuffleConfig} />
                    </Card>
                </TabPane>
                <TabPane tab="Storage Analysis" key="2">
                    <Table dataSource={tables} columns={tableColumns} rowKey="table_name" />
                </TabPane>
            </Tabs>
        </div>
    );
};

const SlowJobList = () => {
    const [jobs, setJobs] = useState([]);
    const [selectedJob, setSelectedJob] = useState(null);

    useEffect(() => {
        axios.get('/api/slow-jobs').then(res => setJobs(res.data));
    }, []);

    const columns = [
        { title: 'App ID', dataIndex: 'app_id' },
        { title: 'Name', dataIndex: 'name' },
        { title: 'Duration (min)', dataIndex: 'duration', render: v => (v / 60000).toFixed(1), sorter: (a, b) => a.duration - b.duration },
        { title: 'Slow Reason', dataIndex: 'slow_reason', render: t => t ? <Tag color="red">{t}</Tag> : '-' },
        { 
            title: 'Action', 
            key: 'action', 
            render: (_, record) => <Button type="link" onClick={() => setSelectedJob(record.app_id)}>View Details</Button> 
        }
    ];

    if (selectedJob) {
        return <JobDetail appId={selectedJob} onBack={() => setSelectedJob(null)} />;
    }

    return <Table dataSource={jobs} columns={columns} rowKey="app_id" />;
};

// --- Main App ---
const App = () => {
    const [currentTab, setCurrentTab] = useState('dashboard');

    return (
        <Layout className="layout">
            <Header>
                <div className="logo">BigDataOps</div>
                <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['dashboard']} onClick={e => setCurrentTab(e.key)}>
                    <Menu.Item key="dashboard">Slow Job Analysis</Menu.Item>
                    <Menu.Item key="config">Monitor Config</Menu.Item>
                </Menu>
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <div className="site-layout-content">
                    {currentTab === 'dashboard' ? <SlowJobList /> : <ConfigManager />}
                </div>
            </Content>
        </Layout>
    );
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
