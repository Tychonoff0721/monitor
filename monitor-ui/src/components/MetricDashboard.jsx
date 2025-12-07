import React, { useState, useEffect } from 'react';
import { Card, Col, Row, Select, Empty, Spin } from 'antd';
import { Area } from '@ant-design/plots';
import axios from 'axios';

const { Option } = Select;

const MetricDashboard = () => {
    const [configs, setConfigs] = useState([]);
    const [selectedConfig, setSelectedConfig] = useState(null);
    const [history, setHistory] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        axios.get('/api/configs').then(res => {
            setConfigs(res.data);
            if (res.data.length > 0) {
                setSelectedConfig(res.data[0].id);
            }
        });
    }, []);

    useEffect(() => {
        if (!selectedConfig) return;
        setLoading(true);
        axios.get(`/api/configs/${selectedConfig}/history`).then(res => {
            setHistory(res.data.reverse()); // Show oldest to newest
            setLoading(false);
        });
    }, [selectedConfig]);

    // Parse History Data for Charts
    const chartData = [];
    
    history.forEach(entry => {
        const time = new Date(entry.timestamp).toLocaleTimeString();
        try {
            const result = JSON.parse(entry.result_json);
            
            // Check if result is a Map (grouped by host/component) or a single value
            if (typeof result === 'object' && result !== null) {
                Object.keys(result).forEach(key => {
                    // Try to extract a numeric value. If it's a nested object, look for 'value' or similar, or just take the first number found?
                    // For now, assume result values are numbers (like from sum function) or objects with a numeric field.
                    // The JMX function returns a Map<String, Object>. 
                    let value = 0;
                    const val = result[key];
                    if (typeof val === 'number') {
                        value = val;
                    } else if (typeof val === 'object' && val !== null) {
                        // e.g. { "committed": 123, "init": 456 ... }
                        // Just pick one for demo, or sum them? Let's pick 'used' or 'Value' if exists, else first number
                        const candidates = ['used', 'committed', 'Value', 'QueueLength', 'NumActive'];
                        for (const c of candidates) {
                            if (val[c] !== undefined) {
                                value = Number(val[c]);
                                break;
                            }
                        }
                    }
                    
                    chartData.push({
                        time,
                        group: key,
                        value: value
                    });
                });
            } else if (typeof result === 'number') {
                chartData.push({
                    time,
                    group: 'Total',
                    value: result
                });
            }
        } catch (e) {
            console.error("Failed to parse result json", e);
        }
    });

    const config = {
        data: chartData,
        xField: 'time',
        yField: 'value',
        seriesField: 'group',
        smooth: true,
    };

    return (
        <div>
            <div style={{ marginBottom: 16 }}>
                <span style={{ marginRight: 8 }}>Select Metric:</span>
                <Select 
                    style={{ width: 300 }} 
                    value={selectedConfig} 
                    onChange={setSelectedConfig}
                >
                    {configs.map(c => (
                        <Option key={c.id} value={c.id}>{c.description}</Option>
                    ))}
                </Select>
            </div>

            {loading ? <Spin /> : (
                chartData.length > 0 ? (
                    <Card title="Metric History Analysis">
                        <Area {...config} style={{ height: 400 }} />
                    </Card>
                ) : <Empty description="No history data available yet" />
            )}
        </div>
    );
};

export default MetricDashboard;
