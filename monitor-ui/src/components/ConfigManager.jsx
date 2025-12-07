import React, { useState, useEffect } from 'react';
import { Button, Table, Modal, Form, Input, Select, Space, message, InputNumber } from 'antd';
import axios from 'axios';

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

export default ConfigManager;
